package me.marketdesigners.assignment.lesson.infrastructure.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import me.marketdesigners.assignment.lesson.domain.entity.Lesson
import me.marketdesigners.assignment.lesson.domain.entity.QLesson.lesson
import me.marketdesigners.assignment.lesson.domain.repository.LessonRepositoryCustom
import org.springframework.stereotype.Component
import java.time.LocalDateTime

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Component
class LessonRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : LessonRepositoryCustom {

}