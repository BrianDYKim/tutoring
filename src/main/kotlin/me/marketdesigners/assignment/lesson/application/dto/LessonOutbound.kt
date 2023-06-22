package me.marketdesigners.assignment.lesson.application.dto

import me.marketdesigners.assignment.lesson.domain.entity.Lesson
import java.time.LocalDateTime

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
        val startedAt: LocalDateTime,
    ) {
        companion object {
            // entity로부터 response dto를 추출하는 메소드
            fun fromEntity(lesson: Lesson): LessonOutbound.StartResponse = with(lesson) {
                StartResponse(
                    this.id!!,
                    this.studentId,
                    this.tutorId,
                    this.lessonSubscriptionId,
                    this.lessonTime.startedAt
                )
            }
        }
    }
}