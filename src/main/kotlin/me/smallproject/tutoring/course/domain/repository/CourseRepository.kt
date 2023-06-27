package me.smallproject.tutoring.course.domain.repository


/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Repository
interface CourseRepository : JpaRepository<Course, Long>, CourseRepositoryCustom {
}