package me.marketdesigners.assignment.course.application.service

import me.marketdesigners.assignment.course.application.dto.CourseInbound
import me.marketdesigners.assignment.course.application.dto.CourseOutbound
import me.marketdesigners.assignment.course.application.validator.CourseValidator
import me.marketdesigners.assignment.course.domain.repository.CourseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val courseValidator: CourseValidator
) : CourseService {

    @Transactional(readOnly = true)
    override fun searchAvailableCourses(getRequest: CourseInbound.GetSimpleRequest): List<CourseOutbound.PaginatedResponseData> {
        TODO("Not yet implemented")
    }
}