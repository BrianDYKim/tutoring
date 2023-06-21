package me.marketdesigners.assignment.course.domain.entity.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

/**
 * 수강코스가 지원하는 종류를 나타내는 VO class
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Embeddable
class CourseType(
    @get:Column(name = "is_voice_serves", nullable = false)
    var isVoiceServes: Boolean = false,
    @get:Column(name = "is_chatting_serves", nullable = false)
    var isChattingServes: Boolean = false,
    @get:Column(name = "is_video_serves", nullable = false)
    var isVideoServes: Boolean = false,
)