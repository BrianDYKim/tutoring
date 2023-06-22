package me.marketdesigners.assignment.lessonSubscription.infrastructure.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.LockModeType
import me.marketdesigners.assignment.lessonSubscription.domain.entity.LessonSubscription
import me.marketdesigners.assignment.lessonSubscription.domain.entity.QLessonSubscription.lessonSubscription
import me.marketdesigners.assignment.lessonSubscription.domain.repository.LessonSubscriptionRepositoryCustom
import org.springframework.stereotype.Component

/**
 * @author Brian
 * @since 2023/06/22
 */
@Component
class LessonSubscriptionRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : LessonSubscriptionRepositoryCustom {
    override fun minusLeftCount(subscriptionId: Long): Boolean {
        val foundSubscription = jpaQueryFactory.selectFrom(lessonSubscription)
            .where(lessonSubscription.id.eq(subscriptionId))
            .fetchOne()

        return foundSubscription?.let {
            // 비관적 잠금을 설정하여 동시성을 제어한다
            jpaQueryFactory.update(lessonSubscription)
                .set(lessonSubscription.lessonLeftCount, it.lessonLeftCount - 1)
                .where(lessonSubscription.id.eq(subscriptionId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .execute()

            true
        } ?: false
    }
}