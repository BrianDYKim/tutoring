package me.marketdesigners.assignment.common.exceptions.custom

import me.marketdesigners.assignment.common.error.ErrorCode
import me.marketdesigners.assignment.common.exceptions.BusinessException

/**
 * 수강권이 만료된 경우 발생하는 예외
 * @author Doyeop Kim
 * @since 2023/06/23
 */
class SubscriptionExpiredException(
    override val errorCode: ErrorCode = ErrorCode.SUBSCRIPTION_EXPIRED_ERROR
) : BusinessException(errorCode) {
}