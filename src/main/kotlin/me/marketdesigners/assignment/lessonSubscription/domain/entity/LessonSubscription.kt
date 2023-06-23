package me.marketdesigners.assignment.lessonSubscription.domain.entity

import jakarta.persistence.*
import me.marketdesigners.assignment.common.entity.BaseEntity
import me.marketdesigners.assignment.lessonSubscription.domain.entity.enums.SubscriptionLanguage
import me.marketdesigners.assignment.lessonSubscription.domain.entity.vo.LessonCountInfo
import me.marketdesigners.assignment.lessonSubscription.domain.entity.vo.SubscriptionPeriod
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
    @get:Column(name = "language", nullable = false)
    @get:Enumerated(EnumType.STRING)
    var language: SubscriptionLanguage = SubscriptionLanguage.ENGLISH,
    @get:Column(name = "student_id", nullable = false)
    var studentId: Long = 0,
    @get:Column(name = "course_id", nullable = false)
    var courseId: Long = 0,
    @get:Column(name = "purchase_price", nullable = false)
    var purchasePrice: Int = 0,
    @get:Embedded
    var lessonCountInfo: LessonCountInfo = LessonCountInfo(),
    @get:Embedded
    var subscriptionType: SubscriptionType = SubscriptionType(),
    @get:Embedded
    var subscriptionPeriod: SubscriptionPeriod = SubscriptionPeriod(),
) : BaseEntity() {
    // 수업당 튜터에게 반환해주는 금액을 계산하여 반환하는 메소드
    fun calculateRevenuePerLesson(): Int {
        return this.purchasePrice / this.lessonCountInfo.lessonTotalCount
    }

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