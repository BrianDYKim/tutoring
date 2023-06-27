package me.smallproject.tutoring.common.exceptions.custom

import me.smallproject.tutoring.common.error.ErrorCode
import me.smallproject.tutoring.common.exceptions.BusinessException


/**
 * @author Doyeop Kim
 * @since 2023/06/25
 */
class LessonAlreadyFinishedException(
    override val errorCode: ErrorCode = ErrorCode.LESSON_ALREADY_FINISHED_ERROR
) : BusinessException(errorCode) {
}