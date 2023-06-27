package me.smallproject.tutoring.lesson.domain.entity.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDateTime


/**
 * 수업의 시작 시간, 종료 시간을 다루는 vo 객체
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Embeddable
data class LessonTime(
    @get:Column(name = "started_at", nullable = false, updatable = false)
    var startedAt: LocalDateTime = LocalDateTime.now(),
    @get:Column(name = "finished_at", nullable = true)
    var finishedAt: LocalDateTime? = null,
) {
    // 수업이 이미 종료되어있는 상태인지 여부를 반환하는 메소드
    fun hasAlreadyFinished(): Boolean {
        return this.finishedAt != null
    }

    // 종료 시간을 갱신하는 메소드
    fun updateFinishedTime(datetime: LocalDateTime): LessonTime {
        return this.apply {
            this.finishedAt = datetime
        }
    }
}