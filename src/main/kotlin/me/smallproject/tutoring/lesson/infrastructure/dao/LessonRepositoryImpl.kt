package me.smallproject.tutoring.lesson.infrastructure.dao

import com.querydsl.jpa.impl.JPAQueryFactory
import me.smallproject.tutoring.lesson.domain.repository.LessonRepositoryCustom
import org.springframework.stereotype.Component


/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Component
class LessonRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : LessonRepositoryCustom {

}