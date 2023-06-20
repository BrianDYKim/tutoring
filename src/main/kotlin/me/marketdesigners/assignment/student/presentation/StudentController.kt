package me.marketdesigners.assignment.student.presentation

import me.marketdesigners.assignment.student.application.service.StudentService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Brian
 * @since 2023/06/20
 */
@RestController
@RequestMapping("/api/students")
class StudentController(
    private val studentService: StudentService,
) {
}