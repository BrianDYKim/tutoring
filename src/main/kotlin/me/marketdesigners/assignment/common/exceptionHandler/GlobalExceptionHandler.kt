package me.marketdesigners.assignment.common.exceptionHandler

import me.marketdesigners.assignment.common.error.ErrorCode
import me.marketdesigners.assignment.common.exceptions.BusinessException
import me.marketdesigners.assignment.common.result.ErrorResults
import me.marketdesigners.assignment.common.result.ResponseGenerator
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * @author Brian
 * @since 2023/06/20
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    // @Valid 어노테이션에 의해 걸러진 경우 예외를 처리해주는 메소드
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResults.Response> {
        val responseBody = ResponseGenerator.getFieldErrorResponse(exception.bindingResult)

        return ResponseEntity.status(400)
            .body(responseBody)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleAllBusinessExceptions(exception: BusinessException): ResponseEntity<ErrorResults.Response> {
        val responseBody = ResponseGenerator.getErrorResponse(exception.errorCode)

        return ResponseEntity.status(exception.errorCode.status)
            .body(responseBody)
    }

    @ExceptionHandler(Exception::class)
    fun handleUnknownExceptions(exception: Exception): ResponseEntity<ErrorResults.Response> {
        val responseBody = ResponseGenerator.getErrorResponse(ErrorCode.UNKNOWN_ERROR)

        return ResponseEntity.status(500)
            .body(responseBody)
    }
}