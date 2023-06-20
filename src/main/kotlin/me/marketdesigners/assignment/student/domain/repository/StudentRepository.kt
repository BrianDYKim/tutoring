package me.marketdesigners.assignment.student.domain.repository

import me.marketdesigners.assignment.student.domain.entity.Student
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @author Brian
 * @since 2023/06/20
 */
interface StudentRepository : JpaRepository<Student, Long>, StudentRepositoryCustom {
}