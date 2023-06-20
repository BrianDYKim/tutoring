package me.marketdesigners.assignment.course.application.dto

import me.marketdesigners.assignment.course.domain.vo.CourseType
import me.marketdesigners.assignment.course.domain.vo.SellingInfo
import java.time.LocalDateTime

/**
 * 수강코스에 대한 response dto 들을 정의하는 sealed class
 * @author Doyeop Kim
 * @since 2023/06/20
 */
sealed class CourseOutbound {
    // 목록 조회시 사용되는 data 객체
    data class PaginatedResponseData(
        val id: Long,
        val language: String,
        val courseType: CourseType,
        val courseDuration: Int,
        val lessonTime: Int,
        val lessonCount: Int,
        val price: Int,
        val sellingInfo: SellingInfo,
        val createdAt: LocalDateTime,
    )

    // 목록 조회시 데이터의 목록과 함께 데이터의 총 개수도 저장해주는 response dto 객체
    data class PaginatedResponse(
        val totalElements: Long,
        val items: List<PaginatedResponseData>
    )
}