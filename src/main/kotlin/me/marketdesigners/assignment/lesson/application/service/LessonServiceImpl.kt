package me.marketdesigners.assignment.lesson.application.service

import me.marketdesigners.assignment.lesson.application.dto.LessonInbound
import me.marketdesigners.assignment.lesson.application.dto.LessonOutbound
import me.marketdesigners.assignment.lesson.application.validator.LessonValidator
import me.marketdesigners.assignment.lesson.domain.repository.LessonRepository
import me.marketdesigners.assignment.lessonSubscription.domain.repository.LessonSubscriptionRepository
import me.marketdesigners.assignment.tutor.domain.repository.TutorRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Service
class LessonServiceImpl(
    private val lessonValidator: LessonValidator,
    private val lessonRepository: LessonRepository,
    private val lessonSubscriptionRepository: LessonSubscriptionRepository,
    private val tutorRepository: TutorRepository,
) : LessonService {
    @Transactional
    override fun startLesson(startRequest: LessonInbound.StartRequest): LessonOutbound.StartResponse {
        // 검증을 수행
        lessonValidator.isStartAvailable(startRequest)

        // 수업 엔티티를 생성하면서 시작 시간과 튜터가 받아가는 정산금을 반영
        val subscription =
            lessonSubscriptionRepository.findByIdOrNull(startRequest.lessonSubscriptionId)!!

        // lesson entity에 tutor에게 정산해야할 금액을 같이 저장해준다
        val lesson = startRequest.toEntity()
        val revenueUpdatedLesson = lesson.updateRevenue(subscription.calculateRevenuePerLesson())
        val savedLesson = lessonRepository.save(revenueUpdatedLesson)

        // 수강권의 남은 횟수를 하나 차감한다
        this.lessonSubscriptionRepository.minusLeftCount(savedLesson.lessonSubscriptionId)

        return LessonOutbound.StartResponse.fromEntity(savedLesson)
    }

    @Transactional
    override fun endLesson(endRequest: LessonInbound.EndRequest): LessonOutbound.EndResponse {
        // 1. 검증 수행
        lessonValidator.isEndAvailable(endRequest)

        // 2. 수업의 마침 시간을 새로 갱신한다
        val foundLesson = lessonRepository.findByIdOrNull(endRequest.lessonId)!!
        val finishedTimeUpdatedLesson = foundLesson.updateFinishedTime()
        val savedLesson = lessonRepository.save(finishedTimeUpdatedLesson)

        // 3. 튜터에게 수업료를 정산한다
        tutorRepository.settleAmountByIdAndAmount(endRequest.tutorId, savedLesson.tutorRevenue)

        // 4. (Optional) 학생의 이메일로 수업 결과를 전송한다

        return LessonOutbound.EndResponse.fromEntity(savedLesson)
    }
}