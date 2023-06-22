package me.marketdesigners.assignment.lesson.domain.entity.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDateTime

/**
 * 수업의 시작 시간, 종료 시간을 다루는 vo 객체
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Embeddable
class LessonTime(
    @get:Column(name = "started_at", nullable = false, updatable = false)
    var startedAt: LocalDateTime = LocalDateTime.now(),
    @get:Column(name = "finished_at", nullable = true)
    var finishedAt: LocalDateTime? = null,
)