package me.marketdesigners.assignment.common.result

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
sealed class SuccessResults {
    // 단일 데이터에 대한 성공 응답 정의
    data class Single<T>(
        val item: T,
        val success: Boolean = false,
    )

    // 복수개의 데이터에 대한 성공 응답 정의
    data class Multiple<T>(
        val items: List<T>,
        val success: Boolean = false,
    )

    // 페이지네이션 된 성공 응답 정의
    data class Paginated<T>(
        val totalPages: Int,
        val totalElements: Int,
        val page: Int,
        val elements: Int,
        val items: List<T>,
        val success: Boolean = false,
    )
}