plugins {
    id("java-library")
    id("chirp.spring-boot-service")
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(projects.common)

    implementation(libs.spring.boot.starter.amqp)

    testImplementation(kotlin("test"))
}