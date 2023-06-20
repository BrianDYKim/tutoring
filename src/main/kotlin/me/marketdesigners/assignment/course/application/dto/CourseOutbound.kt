package me.marketdesigners.assignment.course.application.dto

import me.marketdesigners.assignment.course.domain.entity.Course
import me.marketdesigners.assignment.course.domain.vo.CourseType
import me.marketdesigners.assignment.course.domain.vo.SellingInfo
import java.time.LocalDateTime

/**
 * 수강코스에 대한 response dto 들을 정의하는 sealed class
 * @author Doyeop Kim
 * @since 2023/06/20
 */
sealed class CourseOutbound {
    // 목록 조회에서 사용되는 response dto
    data class GetSimpleResponse(
        val id: Long,
        val language: String,
        val courseType: CourseType,
        val courseDuration: Int,
        val lessonTime: Int,
        val lessonCount: Int,
        val price: Int,
        val sellingInfo: SellingInfo,
        val createdAt: LocalDateTime,
    ) {
        companion object {
            fun of(course: Course): GetSimpleResponse = with(course) {
                GetSimpleResponse(
                    this.id!!,
                    this.language,
                    this.courseType,
                    this.courseDuration,
                    this.lessonTime,
                    this.lessonsCount,
                    this.price,
                    this.sellingInfo,
                    this.createdAt
                )
            }
        }
    }
}