package me.marketdesigners.assignment.lessonSubscription.domain.entity.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.time.LocalDate

/**
 * @author Doyeop Kim
 * @since 2023/06/23
 */
@Embeddable
data class SubscriptionPeriod(
    @get:Column(name = "start_date", nullable = false)
    var startDate: LocalDate = LocalDate.now(),
    @get:Column(name = "end_date", nullable = false)
    var endDate: LocalDate = LocalDate.now(),
) {
    // 현재 날짜 기준으로 해당 수강권이 만료되어있는지 여부를 반환해주는 메소드
    fun hasExpired(): Boolean {
        return LocalDate.now().isAfter(this.endDate)
    }
}