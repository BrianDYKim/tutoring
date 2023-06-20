package me.marketdesigners.assignment.common.error

/**
 * Business logic 처리 과정에서 발생할 수 있는 에러코드를 정의하는 enum class
 * @author Doyeop Kim
 * @since 2023/06/20
 */
enum class ErrorCode(
    val status: Int,
    val message: String
) {
    INPUT_ERROR(400, "입력값이 올바르지 않습니다"),
    ARGUMENT_ERROR(400, "입력값이 비어있거나 없습니다"),
    REQUEST_VALIDATION_ERROR(400, "입력값이 유효하지 않습니다"),
    RESOURCE_DUPLICATION_ERROR(400, "이미 존재합니다"),
    AUTHENTICATION_ERROR(401, "허용되지 않은 접근입니다"),
    AUTHORIZATION_ERROR(403, "올바르지 않은 권한입니다"),
    RESOURCE_NOT_FOUND_ERROR(404, "리소스가 존재하지 않습니다"),
}