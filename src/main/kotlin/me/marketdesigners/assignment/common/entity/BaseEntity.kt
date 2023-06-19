package me.marketdesigners.assignment.common.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * 모든 entity가 공통으로 가지는 속성들을 정의하는 클래스
 * @author Brian
 * @since 2023/06/20
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity(
    @get:CreatedDate
    @get:Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @get:LastModifiedDate
    @get:Column(name = "updated_at", nullable = true)
    var updatedAt: LocalDateTime? = null,
    @get:Column(name = "deleted_at", nullable = true)
    var deletedAt: LocalDateTime? = null,
    @get:Column(name = "is_deleted", nullable = false)
    var isDeleted: Boolean = false,
)