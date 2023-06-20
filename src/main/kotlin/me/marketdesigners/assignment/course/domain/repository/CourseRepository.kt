package me.marketdesigners.assignment.course.domain.repository

import me.marketdesigners.assignment.course.domain.entity.Course
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Repository
interface CourseRepository : JpaRepository<Course, Long>, CourseRepositoryCustom {
}