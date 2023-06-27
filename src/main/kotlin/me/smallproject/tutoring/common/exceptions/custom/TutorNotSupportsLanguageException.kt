package me.smallproject.tutoring.common.exceptions.custom

import me.smallproject.tutoring.common.error.ErrorCode
import me.smallproject.tutoring.common.exceptions.BusinessException


/**
 * 튜터가 특정 언어를 지원하지 않는 경우 발생하는 예외
 * @author Doyeop Kim
 * @since 2023/06/23
 */
class TutorNotSupportsLanguageException(
    override val errorCode: ErrorCode = ErrorCode.TUTOR_NOT_SUPPORTS_LANGUAGE_ERROR
) : BusinessException(errorCode) {
}