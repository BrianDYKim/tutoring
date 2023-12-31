package me.smallproject.tutoring.lesson.application.service

import me.smallproject.tutoring.lesson.application.dto.LessonInbound
import me.smallproject.tutoring.lesson.application.dto.LessonOutbound


/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
interface LessonService {
    // 수업 시작을 처리하는 메소드
    fun startLesson(startRequest: LessonInbound.StartRequest): LessonOutbound.StartResponse

    // 수업 종료를 처리하는 메소드
    fun endLesson(endRequest: LessonInbound.EndRequest): LessonOutbound.EndResponse
}