package me.marketdesigners.assignment.lesson.application.service

import me.marketdesigners.assignment.lesson.application.dto.LessonInbound
import me.marketdesigners.assignment.lesson.application.dto.LessonOutbound
import me.marketdesigners.assignment.lesson.application.validator.LessonValidator
import me.marketdesigners.assignment.lesson.domain.repository.LessonRepository
import me.marketdesigners.assignment.lessonSubscription.domain.repository.LessonSubscriptionRepository
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
) : LessonService {
    @Transactional
    override fun startLesson(startRequest: LessonInbound.StartRequest): LessonOutbound.StartResponse {
        // 검증을 수행
        this.lessonValidator.isStartAvailable(startRequest)

        // 수업 엔티티를 생성하면서 시작 시간을 갱신
        val lesson = startRequest.toEntity()
        val savedLesson = lessonRepository.save(lesson)

        // 수강권의 남은 횟수를 하나 차감한다
        this.lessonSubscriptionRepository.minusLeftCount(savedLesson.lessonSubscriptionId)

        return LessonOutbound.StartResponse.fromEntity(savedLesson)
    }
}