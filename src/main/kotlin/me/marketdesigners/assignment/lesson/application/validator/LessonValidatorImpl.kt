package me.marketdesigners.assignment.lesson.application.validator

import me.marketdesigners.assignment.common.entity.BaseEntity
import me.marketdesigners.assignment.common.error.ErrorCode
import me.marketdesigners.assignment.common.exceptions.custom.*
import me.marketdesigners.assignment.lesson.application.dto.LessonInbound
import me.marketdesigners.assignment.lesson.domain.repository.LessonRepository
import me.marketdesigners.assignment.lessonSubscription.domain.entity.LessonSubscription
import me.marketdesigners.assignment.lessonSubscription.domain.repository.LessonSubscriptionRepository
import me.marketdesigners.assignment.student.domain.repository.StudentRepository
import me.marketdesigners.assignment.tutor.domain.entity.Tutor
import me.marketdesigners.assignment.tutor.domain.repository.TutorRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
@Component
class LessonValidatorImpl(
    private val studentRepository: StudentRepository,
    private val tutorRepository: TutorRepository,
    private val lessonSubscriptionRepository: LessonSubscriptionRepository,
    private val lessonRepository: LessonRepository,
) : LessonValidator {
    // 수업 시작이 가능한지 검증하는 메소드
    override fun isStartAvailable(startRequest: LessonInbound.StartRequest) {
        val foundStudent = studentRepository.findByIdOrNull(startRequest.studentId)

        // 학생이 null이거나, 혹은 삭제된 학생이면 예외처리한다
        rejectIfEntityNotExists(foundStudent, ErrorCode.STUDENT_NOT_FOUND_ERROR)

        val foundTutor = tutorRepository.findByIdOrNull(startRequest.tutorId)

        // 튜터가 null이거나, 혹은 삭제된 상태이면 예외처리한다
        rejectIfEntityNotExists(foundTutor, ErrorCode.TUTOR_NOT_FOUND_ERROR)

        // 수강권이 null 이거나, 혹은 삭제된 상태이면 예외처리한다
        val foundSubscription = lessonSubscriptionRepository.findByIdOrNull(startRequest.lessonSubscriptionId)

        rejectIfEntityNotExists(foundSubscription, ErrorCode.SUBSCRIPTION_NOT_FOUND_ERROR)

        // 수강권의 잔여 횟수가 0 이하인 경우 예외처리한다
        if (!foundSubscription!!.lessonCountInfo.hasPositiveLeftCount()) {
            throw SubscriptionNotLeftException()
        }

        // 수강권의 기간이 지난 경우 예외처리한다
        if(foundSubscription.subscriptionPeriod.hasExpired()) {
            throw SubscriptionExpiredException()
        }

        // 수강권의 언어와 튜터의 언어를 비교
        if (foundSubscription.language.toString() != foundTutor!!.language.toString()) {
            throw TutorNotSupportsLanguageException()
        }

        // 튜터가 해당 수강권의 수업을 진행할 수 없는 경우 예외를 발생한다
        rejectIfTutorCannotSupportLessonType(foundSubscription, foundTutor)
    }

    // 수업 종료가 가능한지 여부를 검증하는 메소드
    override fun isEndAvailable(endRequest: LessonInbound.EndRequest) {
        // Lesson이 존재하지 않으면 예외를 발생시킨다
        val foundLesson = lessonRepository.findByIdOrNull(endRequest.lessonId)

        rejectIfEntityNotExists(foundLesson, ErrorCode.LESSON_NOT_FOUND_ERROR)

        // Lesson에 배정된 튜터가 아닌 경우 예외를 발생시킨다
        if (foundLesson!!.tutorId != endRequest.tutorId) {
            throw UnauthorizedTutorException()
        }

        // lesson이 이미 종료되어있는 경우에는 예외를 발생시킨다
        if (foundLesson.lessonTime.hasAlreadyFinished()) {
            throw LessonAlreadyFinishedException()
        }

        // Tutor가 존재하지 않는 경우 예외를 발생시킨다
        val foundTutor = tutorRepository.findByIdOrNull(endRequest.tutorId)

        rejectIfEntityNotExists(foundTutor, ErrorCode.TUTOR_NOT_FOUND_ERROR)
    }

    /**
     * 엔티티의 존재성을 검증하여 유효하지 않으면 reject 시키는 메소드
     * @param entity BaseEntity를 상속한 도메인 객체
     * @param errorCode reject시 같이 전송해줄 에러 객체
     */
    private fun <T: BaseEntity> rejectIfEntityNotExists(entity: T?, errorCode: ErrorCode) {
        entity?.let {
            if (it.isDeleted) throw ResourceNotFoundException(errorCode)
        } ?: throw ResourceNotFoundException(errorCode)
    }

    // 수강권과 튜터의 지원 타입을 비교하여 검증하는 메소드
    private fun rejectIfTutorCannotSupportLessonType(subscription: LessonSubscription, tutor: Tutor) {
        val subscriptionType = subscription.subscriptionType
        val tutorType = tutor.tutorType

        // 튜터가 false 이지만 수강권이 true인 조건이 하나라도 존재하면 결과는 false를 반환
        val isChattingNotMatched = !tutorType.isChattingAvailable && subscriptionType.isChattingAvailable
        val isVoiceNotMatched = !tutorType.isVoiceAvailable && subscriptionType.isVoiceAvailable
        val isVideoNotMatched = !tutorType.isVideoAvailable && subscriptionType.isVideoAvailable

        if (isChattingNotMatched || isVoiceNotMatched || isVideoNotMatched) {
            throw TutorNotSupportsLessonTypeException()
        }
    }
}