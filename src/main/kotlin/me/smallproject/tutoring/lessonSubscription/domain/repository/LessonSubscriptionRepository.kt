package me.smallproject.tutoring.lessonSubscription.domain.repository


/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
@Repository
interface LessonSubscriptionRepository : JpaRepository<LessonSubscription, Long>, LessonSubscriptionRepositoryCustom {
}