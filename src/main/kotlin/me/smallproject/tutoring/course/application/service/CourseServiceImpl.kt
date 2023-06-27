package me.smallproject.tutoring.course.application.service

import me.smallproject.tutoring.course.application.dto.CourseInbound
import me.smallproject.tutoring.course.application.dto.CourseOutbound
import me.smallproject.tutoring.course.application.validator.CourseValidator
import me.smallproject.tutoring.course.domain.repository.CourseRepository
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
    override fun searchAvailableCourses(getRequest: CourseInbound.GetSimpleRequest): CourseOutbound.PaginatedResponse {
        return this.courseRepository.searchCoursesByCriteria(getRequest)
    }
}