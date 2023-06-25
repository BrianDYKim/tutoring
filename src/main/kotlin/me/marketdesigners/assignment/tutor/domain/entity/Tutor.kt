package me.marketdesigners.assignment.tutor.domain.entity

import jakarta.persistence.*
import me.marketdesigners.assignment.common.entity.BaseEntity
import me.marketdesigners.assignment.tutor.domain.entity.enums.TutorLanguage
import me.marketdesigners.assignment.tutor.domain.entity.vo.TutorType
import java.math.BigInteger

/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
@Entity
@Table(name = "tutors")
class Tutor(
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0,
    @get:Column(name = "email", nullable = false)
    var email: String = "",
    @get:Column(name = "password", nullable = false)
    var password: String = "",
    @get:Column(name = "nickname", nullable = false)
    var nickname: String = "",
    @get:Column(name = "language", nullable = false)
    @get:Enumerated(EnumType.STRING)
    var language: TutorLanguage = TutorLanguage.ENGLISH,
    @get:Column(name = "unsettled_amount", nullable = false)
    var unsettiledAmount: BigInteger = BigInteger.valueOf(0),
    @get:Embedded
    var tutorType: TutorType = TutorType(),
) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        if (javaClass != other?.javaClass) return false

        val that = other as Tutor

        if (id != that.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}