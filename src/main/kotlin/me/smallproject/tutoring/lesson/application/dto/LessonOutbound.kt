package me.smallproject.tutoring.lesson.application.dto

import me.smallproject.tutoring.lesson.domain.entity.Lesson
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
            fun fromEntity(lesson: Lesson): StartResponse = with(lesson) {
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

    // 수업 종료에 대한 response dto
    data class EndResponse(
        val id: Long,
        val studentId: Long,
        val tutorId: Long,
        val startedAt: LocalDateTime,
        val finishedAt: LocalDateTime,
    ) {
        companion object {
            fun fromEntity(lesson: Lesson): EndResponse = with(lesson) {
                EndResponse(
                    this.id!!,
                    this.studentId,
                    this.tutorId,
                    this.lessonTime.startedAt,
                    this.lessonTime.finishedAt!!,
                )
            }
        }
    }
}