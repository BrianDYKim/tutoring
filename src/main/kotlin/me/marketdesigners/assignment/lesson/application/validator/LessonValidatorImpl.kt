package me.marketdesigners.assignment.lesson.application.validator

import me.marketdesigners.assignment.lesson.application.dto.LessonInbound
import me.marketdesigners.assignment.lessonSubscription.domain.repository.LessonSubscriptionRepository
import me.marketdesigners.assignment.student.domain.repository.StudentRepository
import me.marketdesigners.assignment.tutor.domain.repository.TutorRepository
import org.springframework.stereotype.Component

/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
@Component
class LessonValidatorImpl(
    private val studentRepository: StudentRepository,
    private val tutorRepository: TutorRepository,
    private val lessonSubscriptionRepository: LessonSubscriptionRepository,
) : LessonValidator {
    override fun isStartAvailable(startRequest: LessonInbound.StartRequest) {
        TODO("Not yet implemented")
    }
}