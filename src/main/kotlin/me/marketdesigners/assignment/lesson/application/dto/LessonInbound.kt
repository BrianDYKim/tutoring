package me.marketdesigners.assignment.lesson.application.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

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
    )
}