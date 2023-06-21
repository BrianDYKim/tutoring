package me.marketdesigners.assignment.course.application.dto

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

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
        var pageable: Pageable
    ) {
        companion object {
            fun of(
                language: String,
                isVoiceAvailable: Boolean,
                isChatAvailable: Boolean,
                isVideoAvailable: Boolean,
                page: Int,
                pageSize: Int
            ): GetSimpleRequest {
                // page, pageSize를 sanitize 하여서 pageable을 선언한다
                val sanitizedPage = when(page < 0) {
                    true -> 0
                    false -> page
                }

                val sanitizedPageSize = when(pageSize <= 0) {
                    true -> 10
                    false -> pageSize
                }

                val pageable = PageRequest.of(sanitizedPage, sanitizedPageSize)

                return GetSimpleRequest(language, isVoiceAvailable, isChatAvailable, isVideoAvailable, pageable)
            }
        }
    }
}