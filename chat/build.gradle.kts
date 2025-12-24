plugins {
    id("java-library")
    id("chirp.spring-boot-service")
    alias(libs.plugins.kotlin.jpa)
}

dependencies {
    implementation(projects.common)

    testImplementation(kotlin("test"))
}