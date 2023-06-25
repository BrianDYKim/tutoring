package me.marketdesigners.assignment.tutor.domain.repository

import me.marketdesigners.assignment.tutor.domain.entity.Tutor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
@Repository
interface TutorRepository : JpaRepository<Tutor, Long>, TutorRepositoryCustom {
}