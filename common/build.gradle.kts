plugins {
    id("java-library")
    id("chirp.kotlin-common")
}

dependencies {
    api(libs.kotlin.reflect)
    api(libs.jackson.module.kotlin)

    implementation(libs.spring.boot.starter.amqp)

    testImplementation(kotlin("test"))
}