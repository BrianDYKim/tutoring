rootProject.name = "tutoring"

pluginManagement {
    val kotlinVersion = "1.8.21"
    val springBootVersion = "3.1.0"
    val dependencyManagementVersion = "1.1.0"
    val queryDslPluginVersion = "1.0.10"

    plugins {
        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version dependencyManagementVersion
        id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
        id("com.ewerk.gradle.plugins.querydsl") version queryDslPluginVersion

        kotlin("jvm") version kotlinVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.jpa") version kotlinVersion

        kotlin("kapt") version kotlinVersion
    }
}