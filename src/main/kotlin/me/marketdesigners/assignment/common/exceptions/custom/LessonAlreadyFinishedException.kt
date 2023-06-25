package me.marketdesigners.assignment.common.exceptions.custom

import me.marketdesigners.assignment.common.error.ErrorCode
import me.marketdesigners.assignment.common.exceptions.BusinessException

/**
 * @author Doyeop Kim
 * @since 2023/06/25
 */
class LessonAlreadyFinishedException(
    override val errorCode: ErrorCode = ErrorCode.LESSON_ALREADY_FINISHED_ERROR
) : BusinessException(errorCode) {
}