package me.marketdesigners.assignment.tutor.domain.entity.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * 튜터가 지원하는 수업 종류들을 저장하는 VO class
 * @author Doyeop Kim
 * @since 2023/06/22
 */
@Embeddable
data class TutorType(
    @get:Column(name = "is_voice_available", nullable = false)
    var isVoiceAvailable: Boolean = false,
    @get:Column(name = "is_chatting_available", nullable = false)
    var isChattingAvailable: Boolean = false,
    @get:Column(name = "is_video_available", nullable = false)
    var isVideoAvailable: Boolean = false,
)