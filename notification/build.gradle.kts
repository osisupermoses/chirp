plugins {
    id("java-library")
    id("chirp.spring-boot-service")
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(projects.common)

    implementation(libs.spring.boot.starter.mail)
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.thymeleaf)

    testImplementation(kotlin("test"))
}