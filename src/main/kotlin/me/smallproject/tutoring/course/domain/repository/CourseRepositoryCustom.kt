package me.smallproject.tutoring.course.domain.repository


/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
interface CourseRepositoryCustom {
    // 주어진 조건을 만족하는 수강과정 리스트를 반환하는 메소드
    fun searchCoursesByCriteria(getRequest: CourseInbound.GetSimpleRequest): CourseOutbound.PaginatedResponse
}