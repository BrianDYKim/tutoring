package me.marketdesigners.assignment.course.application.service

import me.marketdesigners.assignment.course.application.dto.CourseInbound
import me.marketdesigners.assignment.course.application.dto.CourseOutbound

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
interface CourseService {
    // 주어진 조건에 맞는 수강코스를 찾아오는 메소드
    fun searchAvailableCourses(getRequest: CourseInbound.GetSimpleRequest): List<CourseOutbound.PaginatedResponseData>
}