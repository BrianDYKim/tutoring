package me.smallproject.tutoring.student.presentation


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