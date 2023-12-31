package me.smallproject.tutoring.lesson.presentation

import jakarta.validation.Valid
import me.smallproject.tutoring.common.result.ResponseGenerator
import me.smallproject.tutoring.common.result.SuccessResults
import me.smallproject.tutoring.lesson.application.dto.LessonInbound
import me.smallproject.tutoring.lesson.application.dto.LessonOutbound
import me.smallproject.tutoring.lesson.application.service.LessonService
import org.springframework.web.bind.annotation.*


/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@RestController
@RequestMapping("/api/lessons")
class LessonController(
    private val lessonService: LessonService,
) {
    // 수업 시작을 처리하는 핸들러 메소드
    @PostMapping("/start")
    fun startLesson(@Valid @RequestBody startRequest: LessonInbound.StartRequest): SuccessResults.Single<LessonOutbound.StartResponse> {
        val startResponse = lessonService.startLesson(startRequest)
        return ResponseGenerator.getSingleDataResponse(startResponse)
    }

    // 수업 종료를 처리하는 핸들러 메소드
    @PutMapping("/end")
    fun endLesson(@Valid @RequestBody endRequest: LessonInbound.EndRequest): SuccessResults.Single<LessonOutbound.EndResponse> {
        val endResponse = lessonService.endLesson(endRequest)
        return ResponseGenerator.getSingleDataResponse(endResponse)
    }
}