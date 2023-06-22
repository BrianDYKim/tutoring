package me.marketdesigners.assignment.lesson.application.service

import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.spyk
import me.marketdesigners.assignment.common.error.ErrorCode
import me.marketdesigners.assignment.common.exceptions.custom.ResourceNotFoundException
import me.marketdesigners.assignment.common.exceptions.custom.SubscriptionNotLeftException
import me.marketdesigners.assignment.common.exceptions.custom.TutorNotSupportsLessonTypeException
import me.marketdesigners.assignment.lesson.application.dto.LessonInbound
import me.marketdesigners.assignment.lesson.application.validator.LessonValidator
import me.marketdesigners.assignment.lesson.application.validator.LessonValidatorImpl
import me.marketdesigners.assignment.lesson.domain.repository.LessonRepository
import me.marketdesigners.assignment.lessonSubscription.domain.entity.LessonSubscription
import me.marketdesigners.assignment.lessonSubscription.domain.entity.vo.SubscriptionType
import me.marketdesigners.assignment.lessonSubscription.domain.repository.LessonSubscriptionRepository
import me.marketdesigners.assignment.student.domain.entity.Student
import me.marketdesigners.assignment.student.domain.repository.StudentRepository
import me.marketdesigners.assignment.tutor.domain.entity.Tutor
import me.marketdesigners.assignment.tutor.domain.entity.vo.TutorType
import me.marketdesigners.assignment.tutor.domain.repository.TutorRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.data.repository.findByIdOrNull

/**
 * @author Doyeop Kim
 * @since 2023/06/22
 */
@ExtendWith(MockKExtension::class)
class LessonServiceTests {
    private lateinit var lessonService: LessonService
    private lateinit var lessonValidator: LessonValidator
    private lateinit var lessonRepository: LessonRepository
    private lateinit var studentRepository: StudentRepository
    private lateinit var tutorRepository: TutorRepository
    private lateinit var lessonSubscriptionRepository: LessonSubscriptionRepository

    @BeforeEach
    fun testSetUp() {
        lessonRepository = mockk(relaxed = true, relaxUnitFun = true)
        studentRepository = mockk(relaxed = true, relaxUnitFun = true)
        tutorRepository = mockk(relaxed = true, relaxUnitFun = true)
        lessonSubscriptionRepository = mockk(relaxed = true, relaxUnitFun = true)
        lessonValidator = spyk(LessonValidatorImpl(studentRepository, tutorRepository, lessonSubscriptionRepository))
        lessonService = spyk(LessonServiceImpl(lessonValidator, lessonRepository))
    }

    @ParameterizedTest
    @CsvSource(value = ["1,1,1", "2,1,1"])
    fun `해당 식별자를 가지는 학생이 존재하지 않아서 실패한다`(studentId: Long, tutorId: Long, lessonSubscriptionId: Long): Unit {
        // given: 둘다 유효하지 않은 학생을 모킹한다
        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        every { studentRepository.findByIdOrNull(1) } returns null
        every { studentRepository.findByIdOrNull(2) } returns Student().apply {
            this.id = 2
            this.isDeleted = true
        }

        // then
        val exception = shouldThrow<ResourceNotFoundException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.STUDENT_NOT_FOUND_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,1,1", "1,2,1"])
    fun `해당 식별자를 가지는 튜터가 존재하지 않아서 실패한다`(studentId: Long, tutorId: Long, lessonSubscriptionId: Long): Unit {
        // given: 학생은 유효하지만, 튜터가 존재하지는 않는 상황을 모킹
        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        every { studentRepository.findByIdOrNull(studentId) } returns Student().apply {
            this.id = studentId
        }
        every { tutorRepository.findByIdOrNull(1) } returns null
        every { tutorRepository.findByIdOrNull(2) } returns Tutor().apply {
            this.id = 2
            this.isDeleted = true
        }

        // then
        val exception = shouldThrow<ResourceNotFoundException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.TUTOR_NOT_FOUND_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,1,1", "1,1,2"])
    fun `학생이 가진 수강권이 존재하지 않아서 실패한다`(studentId: Long, tutorId: Long, lessonSubscriptionId: Long): Unit {
        // given: 학생도 유효하고, 튜터도 유효하지만 수강권이 유효하지 않은 상황을 모킹
        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        every { studentRepository.findByIdOrNull(studentId) } returns Student().apply {
            this.id = studentId
        }
        every { tutorRepository.findByIdOrNull(tutorId) } returns Tutor().apply {
            this.id = tutorId
        }
        every { lessonSubscriptionRepository.findByIdOrNull(1) } returns null
        every { lessonSubscriptionRepository.findByIdOrNull(2) } returns LessonSubscription().apply {
            this.id = 2
            this.isDeleted = true
        }

        // then
        val exception = shouldThrow<ResourceNotFoundException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.SUBSCRIPTION_NOT_FOUND_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["1,1,1"])
    fun `학생이 가진 수강권의 잔여 횟수가 모두 소진되어 실패한다`(studentId: Long, tutorId: Long, lessonSubscriptionId: Long): Unit {
        // given: 엔티티들은 모두 유효하지만, 수강권의 잔여 횟수가 0 이하인 경우 실패 처리
        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        every { studentRepository.findByIdOrNull(studentId) } returns Student().apply {
            this.id = studentId
        }
        every { tutorRepository.findByIdOrNull(tutorId) } returns Tutor().apply {
            this.id = tutorId
        }
        every { lessonSubscriptionRepository.findByIdOrNull(lessonSubscriptionId) } returns LessonSubscription().apply {
            this.id = lessonSubscriptionId
            this.lessonLeftCount = 0
        }

        // then
        val exception = shouldThrow<SubscriptionNotLeftException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.SUBSCRIPTION_NOT_LEFT_ERROR)
    }

    @ParameterizedTest
    @CsvSource(value = ["false,true,false,true,true,true", "true,true,false,true,true,true", "false,true,false,false,true,true"])
    fun `튜터가 지원하는 수업 종류와 수강권의 수업 종류가 일치하지 않아 실패한다`(
        tutorVoiceAvailable: Boolean,
        tutorChattingAvailable: Boolean,
        tutorVideoAvailable: Boolean,
        subscriptionVoiceAvailable: Boolean,
        subscriptionChattingAvailable: Boolean,
        subscriptionVideoAvailable: Boolean,
    ): Unit {
        // given
        val studentId: Long = 1
        val tutorId: Long = 1
        val lessonSubscriptionId: Long = 1
        val startRequest = LessonInbound.StartRequest(studentId, tutorId, lessonSubscriptionId)

        every { studentRepository.findByIdOrNull(studentId) } returns Student().apply {
            this.id = studentId
        }

        every { tutorRepository.findByIdOrNull(tutorId) } returns Tutor().apply {
            this.id = tutorId
            this.tutorType = TutorType(
                isVoiceAvailable = tutorVoiceAvailable,
                isChattingAvailable = tutorChattingAvailable,
                isVideoAvailable = tutorVideoAvailable
            )
        }
        // 수강권 = 음성 o 채팅 o 화상 o
        every { lessonSubscriptionRepository.findByIdOrNull(lessonSubscriptionId) } returns LessonSubscription().apply {
            this.id = lessonSubscriptionId
            this.lessonLeftCount = 30
            this.subscriptionType =
                SubscriptionType(
                    isVoiceAvailable = subscriptionVoiceAvailable,
                    isChattingAvailable = subscriptionChattingAvailable,
                    isVideoAvailable = subscriptionVideoAvailable
                )
        }

        // then
        val exception = shouldThrow<TutorNotSupportsLessonTypeException> { lessonService.startLesson(startRequest) }
        assertEquals(exception.errorCode, ErrorCode.TUTOR_NOT_SUPPORTS_LESSON_TYPE_EXCEPTION)
    }

    @Test
    fun `모든 조건을 만족하여 수업이 성사된다`(): Unit {

    }
}