import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
}

group = "me.max"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation("org.testng:testng:7.5")
    implementation("io.kotest:kotest-runner-junit5-jvm:4.6.0")
    testImplementation("io.mockk:mockk:1.12.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.5")
    implementation("org.springframework.data:spring-data-jdbc:2.3.3")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.6")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.5")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.6")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.0")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.1.0")
    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.10")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}