plugins {
    id("java-library")
    id("chirp.spring-boot-service")
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(projects.common)

    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.data.jpa)
    runtimeOnly(libs.postgresql)

    testImplementation(kotlin("test"))
}