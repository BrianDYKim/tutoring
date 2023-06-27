package me.smallproject.tutoring.tutor.domain.repository


/**
 * @author Doyeop Kim
 * @since 2023/06/21
 */
@Repository
interface TutorRepository : JpaRepository<Tutor, Long>, TutorRepositoryCustom {
}