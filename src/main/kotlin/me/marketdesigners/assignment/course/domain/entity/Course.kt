package me.marketdesigners.assignment.course.domain.entity

import jakarta.persistence.*
import me.marketdesigners.assignment.common.entity.BaseEntity
import me.marketdesigners.assignment.course.domain.entity.enums.CourseLanguage
import me.marketdesigners.assignment.course.domain.entity.vo.CourseType
import me.marketdesigners.assignment.course.domain.entity.vo.SellingInfo

/**
 * @author Doyeop Kim
 * @since 2023/06/20
 */
@Entity
@Table(name = "courses")
class Course(
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @get:Column(name = "language", nullable = false)
    @get:Enumerated(EnumType.STRING)
    var language: CourseLanguage = CourseLanguage.CHINESE,
    @get:Column(name = "course_duration", nullable = false)
    var courseDuration: Int = 0,
    @get:Column(name = "lesson_time", nullable = false)
    var lessonTime: Int = 0,
    @get:Column(name = "lessons_count", nullable = false)
    var lessonsCount: Int = 0,
    @get:Column(name = "price", nullable = false)
    var price: Int = 0,
    @Embedded
    var courseType: CourseType = CourseType(),
    @Embedded
    var sellingInfo: SellingInfo = SellingInfo(),
) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        val that = other as Course

        if (id != that.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}