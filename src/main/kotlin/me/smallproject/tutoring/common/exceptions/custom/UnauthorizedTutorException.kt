package me.smallproject.tutoring.common.exceptions.custom

import me.smallproject.tutoring.common.error.ErrorCode
import me.smallproject.tutoring.common.exceptions.BusinessException


/**
 * 권한이 허용되지 않은 튜터가 요청함에 따라 발생하는 예외
 * @author Brian
 * @since 2023/06/25
 */
class UnauthorizedTutorException(
    override val errorCode: ErrorCode = ErrorCode.UNAUTHORIZED_TUTOR_ERROR
) : BusinessException(errorCode) {
}