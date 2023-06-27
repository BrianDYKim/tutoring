package me.smallproject.tutoring.common.exceptions.custom

import me.smallproject.tutoring.common.error.ErrorCode
import me.smallproject.tutoring.common.exceptions.BusinessException


/**
 * 튜터가 지원하지 않는 수업 유형으로 요청하였기에 발생하는 예외
 * @author Doyeop Kim
 * @since 2023/06/22
 */
class TutorNotSupportsLessonTypeException(
    override val errorCode: ErrorCode = ErrorCode.TUTOR_NOT_SUPPORTS_LESSON_TYPE_ERROR
) : BusinessException(errorCode) {
}