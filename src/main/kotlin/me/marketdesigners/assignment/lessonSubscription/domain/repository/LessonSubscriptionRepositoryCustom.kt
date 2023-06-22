package me.marketdesigners.assignment.lessonSubscription.domain.repository

/**
 * @author Brian
 * @since 2023/06/22
 */
interface LessonSubscriptionRepositoryCustom {

    //
    fun minusLeftCount(subscriptionId: Long): Boolean
}