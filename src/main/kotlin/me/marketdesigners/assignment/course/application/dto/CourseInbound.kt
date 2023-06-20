package me.marketdesigners.assignment.course.application.dto

import org.springframework.data.domain.Pageable

/**
 * 수강코스에 대한 request dto들을 정의하는 sealed class
 * @author Doyeop Kim
 * @since 2023/06/20
 */
sealed class CourseInbound {
    data class GetSimpleRequest(
        val language: String,
        val isVoiceAvailable: Boolean,
        val isChatAvailable: Boolean,
        val isVideoAvailable: Boolean,
        val pageable: Pageable
    )
}