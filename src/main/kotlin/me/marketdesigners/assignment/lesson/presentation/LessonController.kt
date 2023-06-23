package me.marketdesigners.assignment.lesson.presentation

import jakarta.validation.Valid
import me.marketdesigners.assignment.common.result.ResponseGenerator
import me.marketdesigners.assignment.common.result.SuccessResults
import me.marketdesigners.assignment.lesson.application.dto.LessonInbound
import me.marketdesigners.assignment.lesson.application.dto.LessonOutbound
import me.marketdesigners.assignment.lesson.application.service.LessonService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
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
    // 수업 시작을 처리하는 핸들러 메소드
    @PostMapping("/start")
    fun startLesson(@Valid @RequestBody startRequest: LessonInbound.StartRequest): SuccessResults.Single<LessonOutbound.StartResponse> {
        val startResponse = lessonService.startLesson(startRequest)
        return ResponseGenerator.getSingleDataResponse(startResponse)
    }

    // 수업 종료를 처리하는 핸들러 메소드
    @PutMapping("/end")
    fun endLesson(@Valid @RequestBody endRequest: LessonInbound.EndRequest): SuccessResults.Single<LessonOutbound.EndResponse> {
        TODO("Not yet implemented")
    }
}