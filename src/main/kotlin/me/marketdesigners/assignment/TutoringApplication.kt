package me.marketdesigners.assignment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class TutoringApplication

fun main(args: Array<String>) {
    runApplication<TutoringApplication>(*args)
}
