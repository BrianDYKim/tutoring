package me.marketdesigners.assignment.tutor.domain.repository

/**
 * @author Brian
 * @since 2023/06/25
 */
interface TutorRepositoryCustom {
    // 튜터에게 수업 정산 요금을 정산해주는 메소드
    fun settleAmountByIdAndAmount(tutorId: Long, settleAmount: Int): Boolean
}