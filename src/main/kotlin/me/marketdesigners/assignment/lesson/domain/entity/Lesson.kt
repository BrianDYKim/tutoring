package me.marketdesigners.assignment.lesson.domain.entity

import jakarta.persistence.*
import me.marketdesigners.assignment.common.entity.BaseEntity
import me.marketdesigners.assignment.lesson.domain.entity.vo.LessonTime
import java.time.LocalDateTime

/**
 * 수업에 대한 entity를 표현하는 객체
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Entity
@Table(name = "lessons")
class Lesson(
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @get:Column(name = "student_id", nullable = false)
    var studentId: Long = 0,
    @get:Column(name = "tutor_id", nullable = false)
    var tutorId: Long = 0,
    @get:Column(name = "lesson_subscription_id", nullable = false)
    var lessonSubscriptionId: Long = 0,
    @get:Column(name = "tutor_revenue", nullable = false)
    var tutorRevenue: Int = 0,
    @get:Embedded
    var lessonTime: LessonTime = LessonTime(),
) : BaseEntity() {
    // tutorRevenue를 새로 갱신하여 entity를 반환해주는 메소드
    fun updateRevenue(tutorRevenue: Int): Lesson {
        return this.apply {
            this.tutorRevenue = tutorRevenue
        }
    }

    // 종료 시간을 갱신하여 엔티티를 새로 반환해주는 메소드
    fun updateFinishedTime(): Lesson {
        return this.apply {
            this.lessonTime.finishedAt = LocalDateTime.now()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        val that = other as Lesson

        if (id != that.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}