package me.marketdesigners.assignment.common.result

import me.marketdesigners.assignment.common.error.ErrorCode
import org.springframework.validation.BindingResult

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
sealed class ErrorResults {
    /**
     * Error에 대한 Response를 담당하는 data class
     * @param error 에러메시지
     */
    data class Response(val error: String) {
        companion object {
            // 필드에서 에러가 발생한게 아닌 비지니스 로직 처리 과정에서 발생한 에러에 대한 payload
            fun of(errorCode: ErrorCode): Response = Response(errorCode.message)

            // Validation 과정에서 발생한 오류지만, 단 하나의 필드에서만 오류가 발생하는 경우
            fun of(fieldError: FieldError): Response = Response(fieldError.message)

            // Validation 과정에서 발생한 오류지만, 여러개의 필드에서 오류가 발생하는 경우 -> 단 하나의 필드에 대해서만 에러를 내려준다
            fun of(fieldErrorList: List<FieldError>): Response = with(fieldErrorList) {
                Response(this.first().message)
            }

            // Validation 과정에서 발생한 오류지만, BindingResult로 에러가 전달되는 경우
            fun of(bindingResult: BindingResult): Response = with(bindingResult) {
                val message = FieldError.of(this).first().message

                return@with Response(message)
            }
        }
    }

    /**
     * Field에 대한 에러를 담아주는 inner class
     * @param field 에러가 발생한 필드의 이름
     * @param value 에러가 발생한 필드가 원래 가지고 있던 값
     * @param message 에러가 발생한 이유
     */
    class FieldError private constructor(val field: String, val value: String, val message: String) {
        companion object {
            // 에러를 일으키는 필드가 단 하나만 존재하는 경우
            fun of(field: String, value: String, message: String): FieldError = FieldError(field, value, message)

            // BindingResult로 에러가 전달되는 경우
            fun of(bindingResult: BindingResult): List<FieldError> = with(bindingResult) {
                this.fieldErrors.map { of(it.field, it.rejectedValue.toString(), it.defaultMessage!!) }
            }
        }
    }
}