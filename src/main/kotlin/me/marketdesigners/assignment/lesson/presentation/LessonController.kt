package me.marketdesigners.assignment.lesson.presentation

import me.marketdesigners.assignment.lesson.application.service.LessonService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@RestController
@RequestMapping("/api/lessons")
class LessonController(
    private val lessonService: LessonService,
) {
}