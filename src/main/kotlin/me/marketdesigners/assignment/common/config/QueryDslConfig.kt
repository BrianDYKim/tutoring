package me.marketdesigners.assignment.common.config

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Brian
 * @since 2023/06/20
 */
@Configuration
class QueryDslConfig(@PersistenceContext private val em: EntityManager) {
    @Bean
    fun jpaQueryFactory(): JPAQueryFactory = JPAQueryFactory(em)
}