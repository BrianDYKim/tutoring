package me.marketdesigners.assignment.student.domain.entity

import jakarta.persistence.*
import me.marketdesigners.assignment.common.entity.BaseEntity

/**
 * @author Brian
 * @since 2023/06/20
 */
@Entity
@Table(name = "students")
class Student(
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @get:Column(name = "email", nullable = false)
    var email: String = "",
    @get:Column(name = "password", nullable = false)
    var password: String = "",
    @get:Column(name = "nickname", nullable = false)
    var nickname: String = "",
): BaseEntity()