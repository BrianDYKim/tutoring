package me.marketdesigners.assignment.lesson.application.service

import me.marketdesigners.assignment.lesson.application.dto.LessonInbound
import me.marketdesigners.assignment.lesson.application.dto.LessonOutbound

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
interface LessonService {
    // 수업 시작을 처리하는 메소드
    fun startLesson(startRequest: LessonInbound.StartRequest): LessonOutbound.StartResponse
}