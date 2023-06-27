package me.smallproject.tutoring.lessonSubscription.domain.entity.vo


/**
 * 수강권이 지원하는 수업 유형들을 저장하는 vo 클래스
 * @author Doyeop Kim
 * @since 2023/06/22
 */
@Embeddable
data class SubscriptionType(
    @get:Column(name = "is_voice_available", nullable = false)
    var isVoiceAvailable: Boolean = false,
    @get:Column(name = "is_chatting_available", nullable = false)
    var isChattingAvailable: Boolean = false,
    @get:Column(name = "is_video_available", nullable = false)
    var isVideoAvailable: Boolean = false,
)