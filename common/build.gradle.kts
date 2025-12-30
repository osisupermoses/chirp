plugins {
    id("java-library")
    id("chirp.kotlin-common")
}

dependencies {
    api(libs.kotlin.reflect)
    api(libs.jackson.module.kotlin)

    implementation(libs.jwt.api)
    runtimeOnly(libs.jwt.impl)
    runtimeOnly(libs.jwt.jackson)

    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.amqp)

    testImplementation(kotlin("test"))
}