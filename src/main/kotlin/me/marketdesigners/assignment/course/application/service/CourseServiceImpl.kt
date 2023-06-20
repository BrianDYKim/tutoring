package me.marketdesigners.assignment.course.application.service

import jakarta.transaction.Transactional
import me.marketdesigners.assignment.course.application.dto.CourseInbound
import me.marketdesigners.assignment.course.application.dto.CourseOutbound
import org.springframework.stereotype.Service

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Service
class CourseServiceImpl : CourseService {
    @Transactional
    override fun searchAvailableCourses(getRequest: CourseInbound.GetSimpleRequest): List<CourseOutbound.GetSimpleResponse> {
        TODO("Not yet implemented")
    }
}