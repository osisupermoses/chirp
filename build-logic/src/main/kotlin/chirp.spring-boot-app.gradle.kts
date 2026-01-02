import gradle.kotlin.dsl.accessors._46e63eab28c6ec47af58720eaeecc791.allOpen
import gradle.kotlin.dsl.accessors._46e63eab28c6ec47af58720eaeecc791.java

plugins {
    id("chirp.spring-boot-service")
    id("org.springframework.boot")
    kotlin("plugin.spring")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}