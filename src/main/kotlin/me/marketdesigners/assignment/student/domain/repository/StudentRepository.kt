package me.marketdesigners.assignment.student.domain.repository

import me.marketdesigners.assignment.student.domain.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Brian
 * @since 2023/06/20
 */
@Repository
interface StudentRepository : JpaRepository<Student, Long>, StudentRepositoryCustom {
}