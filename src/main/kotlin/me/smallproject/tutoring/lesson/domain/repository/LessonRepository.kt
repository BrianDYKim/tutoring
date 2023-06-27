package me.smallproject.tutoring.lesson.domain.repository

import me.smallproject.tutoring.lesson.domain.entity.Lesson
import org.springframework.data.jpa.repository.JpaRepository


/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
interface LessonRepository : JpaRepository<Lesson, Long>, LessonRepositoryCustom {
}