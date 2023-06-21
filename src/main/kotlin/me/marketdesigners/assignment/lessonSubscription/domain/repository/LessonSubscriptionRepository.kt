package me.marketdesigners.assignment.lessonSubscription.domain.repository

import me.marketdesigners.assignment.lessonSubscription.domain.entity.LessonSubscription
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
@Repository
interface LessonSubscriptionRepository : JpaRepository<LessonSubscription, Long> {
}