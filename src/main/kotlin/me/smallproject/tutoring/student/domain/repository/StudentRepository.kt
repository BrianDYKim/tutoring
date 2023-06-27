package me.smallproject.tutoring.student.domain.repository


/**
 * @author Brian
 * @since 2023/06/20
 */
@Repository
interface StudentRepository : JpaRepository<Student, Long>, StudentRepositoryCustom {
}