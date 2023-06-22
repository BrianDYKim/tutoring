package me.marketdesigners.assignment.lessonSubscription.domain.entity

import jakarta.persistence.*
import me.marketdesigners.assignment.common.entity.BaseEntity
import me.marketdesigners.assignment.lessonSubscription.domain.entity.vo.SubscriptionType

/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
@Entity
@Table(name = "lesson_subscriptions")
class LessonSubscription(
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @get:Column(name = "student_id", nullable = false)
    var studentId: Long = 0,
    @get:Column(name = "course_id", nullable = false)
    var courseId: Long = 0,
    @get:Column(name = "lesson_left_count", nullable = false)
    var lessonLeftCount: Int = 0,
    @get:Embedded
    var subscriptionType: SubscriptionType = SubscriptionType(),
) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        val that = other as LessonSubscription

        if (id != that.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}