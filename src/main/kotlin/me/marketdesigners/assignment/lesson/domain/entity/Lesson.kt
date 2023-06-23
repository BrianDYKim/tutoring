package me.marketdesigners.assignment.lesson.domain.entity

import jakarta.persistence.*
import me.marketdesigners.assignment.common.entity.BaseEntity
import me.marketdesigners.assignment.lesson.domain.entity.vo.LessonTime

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