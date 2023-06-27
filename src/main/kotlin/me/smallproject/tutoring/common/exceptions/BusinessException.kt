package me.smallproject.tutoring.common.exceptions


/**
 * Business logic 처리 과정에서 발생하는 4xx 에러의 조상 예외 클래스
 * @author Doyeop Kim
 * @since 2023/06/20
 */
open class BusinessException(open val errorCode: ErrorCode) : RuntimeException() {
}