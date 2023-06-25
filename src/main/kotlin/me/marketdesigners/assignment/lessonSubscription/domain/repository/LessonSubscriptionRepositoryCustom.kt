package me.marketdesigners.assignment.lessonSubscription.domain.repository

/**
 * @author Brian
 * @since 2023/06/22
 */
interface LessonSubscriptionRepositoryCustom {

    // 수강권의 잔여 횟수를 깎는 메소드
    fun minusLeftCount(subscriptionId: Long): Boolean
}