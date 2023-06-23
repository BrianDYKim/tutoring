package me.marketdesigners.assignment.lessonSubscription.domain.entity.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * 수강권의 구매 당시 총 개수와, 현재 남은 개수를 관리하는 VO class
 * @author Doyeop Kim
 * @since 2023/06/23
 */
@Embeddable
data class LessonCountInfo(
    @get:Column(name = "lesson_total_count", nullable = false)
    var lessonTotalCount: Int = 0,
    @get:Column(name = "lesson_left_count", nullable = false)
    var lessonLeftCount: Int = 0,
) {
    // 남은 수강 횟수가 1 이상인지 여부를 반환하는 메소드
    fun hasPositiveLeftCount(): Boolean {
        return this.lessonLeftCount > 0
    }
}