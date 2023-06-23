package me.marketdesigners.assignment.lesson.application.validator

import me.marketdesigners.assignment.lesson.application.dto.LessonInbound

/**
 * 수업 관련 비지니스 로직 처리에 대한 검증을 수행하는 인터페이스
 * @author Doyeop Kim
 * @since 2023/06/21
 */
interface LessonValidator {
    fun isStartAvailable(startRequest: LessonInbound.StartRequest): Unit
}