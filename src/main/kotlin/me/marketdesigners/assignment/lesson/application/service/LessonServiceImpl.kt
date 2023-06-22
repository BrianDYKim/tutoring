package me.marketdesigners.assignment.lesson.application.service

import me.marketdesigners.assignment.lesson.application.dto.LessonInbound
import me.marketdesigners.assignment.lesson.application.dto.LessonOutbound
import me.marketdesigners.assignment.lesson.application.validator.LessonValidator
import me.marketdesigners.assignment.lesson.domain.repository.LessonRepository
import org.springframework.stereotype.Service

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Service
class LessonServiceImpl(
    private val lessonValidator: LessonValidator,
    private val lessonRepository: LessonRepository,
) : LessonService {
    override fun startLesson(startRequest: LessonInbound.StartRequest): LessonOutbound.StartResponse {
        // 검증을 수행
        this.lessonValidator.isStartAvailable(startRequest)
        TODO("Not yet implemented")
    }
}