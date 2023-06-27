package me.smallproject.tutoring


@SpringBootApplication
@EnableJpaAuditing
class TutoringApplication

fun main(args: Array<String>) {
    runApplication<me.smallproject.tutoring.TutoringApplication>(*args)
}
