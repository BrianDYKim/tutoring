package me.marketdesigners.assignment.common.exceptions.custom

import me.marketdesigners.assignment.common.error.ErrorCode
import me.marketdesigners.assignment.common.exceptions.BusinessException

/**
 * 수강권의 수업 잔여 횟수가 남아있지 않아서 발생하는 예외 클래스
 * @author Doyeop Kim
 * @since 2023/06/22
 */
class SubscriptionNotLeftException(
    override val errorCode: ErrorCode = ErrorCode.SUBSCRIPTION_NOT_LEFT_ERROR
) : BusinessException(errorCode) {
}