package me.smallproject.tutoring.tutor.infrastructure.dao


/**
 * @author Brian
 * @since 2023/06/25
 */
@Component
class TutorRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : TutorRepositoryCustom {
    // 수업에 대한 정산 금액을 컬럼에 새로 갱신해주는 메소드
    override fun settleAmountByIdAndAmount(tutorId: Long, settleAmount: Int): Boolean {
        // 튜터에게 pessimistic_lock을 걸어서 금액이 정산되는 동안 다른 트랜잭션이 간섭하지 못하도록 막는다
        val foundTutorId = jpaQueryFactory.select(tutor.id)
            .from(tutor)
            .where(tutor.id.eq(tutorId))
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .fetchOne()

        return foundTutorId?.run {
            jpaQueryFactory.update(tutor)
                .set(tutor.unsettiledAmount, tutor.unsettiledAmount.add(settleAmount))
                .set(tutor.updatedAt, LocalDateTime.now())
                .where(tutor.id.eq(tutorId))
                .execute() > 0
        } ?: false
    }
}