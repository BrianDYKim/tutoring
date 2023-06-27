package me.smallproject.tutoring.common.exceptions.custom

import me.smallproject.tutoring.common.error.ErrorCode
import me.smallproject.tutoring.common.exceptions.BusinessException


/**
 * 특정 리소스가 발견되지 않았을 때 발생시키는 예외
 * @author Doyeop Kim
 * @since 2023/06/22
 */
class ResourceNotFoundException(
    override val errorCode: ErrorCode = ErrorCode.RESOURCE_NOT_FOUND_ERROR
) : BusinessException(errorCode) {
}