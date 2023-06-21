package me.marketdesigners.assignment.lesson.application.dto

/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
sealed class LessonOutbound {
    // 수업 시작에 대한 response dto
    data class StartResponse(
        val id: Long,
        val studentId: Long,
        val tutorId: Long,
        val lessonSubscriptionId: Long,
        val startedAt: Long,
    )
}