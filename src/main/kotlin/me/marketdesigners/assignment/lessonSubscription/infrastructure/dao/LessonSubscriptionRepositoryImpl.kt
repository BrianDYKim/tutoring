package me.marketdesigners.assignment.lessonSubscription.infrastructure.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.LockModeType
import me.marketdesigners.assignment.lessonSubscription.domain.entity.QLessonSubscription.lessonSubscription
import me.marketdesigners.assignment.lessonSubscription.domain.repository.LessonSubscriptionRepositoryCustom
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * @author Brian
 * @since 2023/06/22
 */
@Component
class LessonSubscriptionRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : LessonSubscriptionRepositoryCustom {
    override fun minusLeftCount(subscriptionId: Long): Boolean {
        // 발견한 수강권에 pessimistic lock을 설정하여 다른 트랜잭션이 해당 레코드를 수정하지 못하도록 설정한다
        val foundSubscription = jpaQueryFactory.selectFrom(lessonSubscription)
            .where(lessonSubscription.id.eq(subscriptionId))
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .fetchOne()

        return foundSubscription?.run {
            jpaQueryFactory.update(lessonSubscription)
                .set(
                    lessonSubscription.lessonCountInfo.lessonLeftCount,
                    lessonSubscription.lessonCountInfo.lessonLeftCount.subtract(1)
                )
                .set(lessonSubscription.updatedAt, LocalDateTime.now())
                .where(lessonSubscription.id.eq(subscriptionId))
                .execute() > 0
        } ?: false
    }
}