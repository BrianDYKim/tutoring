package me.smallproject.tutoring.course.application.service

import me.smallproject.tutoring.course.application.dto.CourseInbound
import me.smallproject.tutoring.course.application.dto.CourseOutbound

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
interface CourseService {
    // 주어진 조건에 맞는 수강코스를 찾아오는 메소드
    fun searchAvailableCourses(getRequest: CourseInbound.GetSimpleRequest): CourseOutbound.PaginatedResponse
}