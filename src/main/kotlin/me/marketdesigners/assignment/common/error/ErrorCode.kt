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

    // Custom Error codes
    STUDENT_NOT_FOUND_ERROR(400, "학생이 존재하지 않습니다"),
    TUTOR_NOT_FOUND_ERROR(400, "튜터가 존재하지 않습니다"),
    SUBSCRIPTION_NOT_FOUND_ERROR(400, "수강권이 존재하지 않습니다"),
    SUBSCRIPTION_NOT_LEFT_ERROR(400, "수강권의 잔여 횟수가 없습니다"),
    TUTOR_NOT_SUPPORTS_LESSON_TYPE_ERROR(400, "튜터가 지원하지 않는 수업 유형입니다"),
    SUBSCRIPTION_EXPIRED_ERROR(400, "수강권이 만료되었습니다"),
    TUTOR_NOT_SUPPORTS_LANGUAGE_ERROR(400, "튜터가 해당 언어를 수업할 수 없습니다"),
    LESSON_NOT_FOUND_ERROR(400, "수업이 존재하지 않습니다"),
    UNAUTHORIZED_TUTOR_ERROR(400, "권한이 존재하지 않습니다"),
}