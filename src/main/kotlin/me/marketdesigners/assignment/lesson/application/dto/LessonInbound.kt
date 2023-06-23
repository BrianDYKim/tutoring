package me.marketdesigners.assignment.lesson.application.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import me.marketdesigners.assignment.lesson.domain.entity.Lesson
import me.marketdesigners.assignment.lesson.domain.entity.vo.LessonTime

/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
sealed class LessonInbound {
    // 수업 시작에 대한 request dto
    data class StartRequest(
        @field:NotNull
        @field:Positive
        val studentId: Long,
        @field:NotNull
        @field:Positive
        val tutorId: Long,
        @field:NotNull
        @field:Positive
        val lessonSubscriptionId: Long,
    ) {
        // request dto -> entity
        fun toEntity(): Lesson {
            val lessonTime = LessonTime()
            return Lesson(null, studentId, tutorId, lessonSubscriptionId, lessonTime)
        }
    }
}