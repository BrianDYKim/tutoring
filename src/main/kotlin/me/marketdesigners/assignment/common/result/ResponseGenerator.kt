package me.marketdesigners.assignment.common.result

import me.marketdesigners.assignment.common.error.ErrorCode
import org.springframework.validation.BindingResult

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
object ResponseGenerator {
    // 성공에 대한 결과를 리턴하는 메소드
    fun getSuccessResponse(): SuccessResults.Single<String> = SuccessResults.Single("OK")

    // 단일 데이터를 가지는 성공에 대한 결과를 리턴하는 메소드
    fun <T> getSingleDataResponse(data: T): SuccessResults.Single<T> = SuccessResults.Single(data)

    // 여러개의 데이터를 가지는 성공에 대한 결과를 리턴하는 메소드
    fun <T> getMultipleDataResponse(dataList: List<T>): SuccessResults.Multiple<T> = SuccessResults.Multiple(dataList)

    // 페이지네이션 된 데이터를 가지는 성공에 대한 결과를 리턴하는 메소드
    fun <T> getPaginatedDataResponse(totalElements: Int, page: Int, dataList: List<T>): SuccessResults.Paginated<T> {
        val totalPages = Math.ceil(totalElements.toDouble() / dataList.size.toDouble()).toInt()

        return SuccessResults.Paginated(totalPages, totalElements, page, dataList.size, dataList)
    }

    // field에서 에러가 발생하지 않는 경우 에러코드만 포함시켜서 반환하는 메소드
    fun getErrorResponse(errorCode: ErrorCode): ErrorResults.Response = ErrorResults.Response.of(errorCode)

    // field 여러개에서 에러가 발생하는 경우
    fun getFieldErrorResponse(fieldErrorList: List<ErrorResults.FieldError>): ErrorResults.Response =
        ErrorResults.Response.of(fieldErrorList)

    // field 검증 과정에서 에러가 발생하였으며, 결과가 bindingResult로 전달되는 경우
    fun getFieldErrorResponse(bindingResult: BindingResult): ErrorResults.Response =
        ErrorResults.Response.of(bindingResult)
}