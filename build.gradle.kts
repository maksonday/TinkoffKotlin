import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id ("org.jetbrains.kotlin.plugin.jpa") version "1.5.21"
    id ("org.jetbrains.kotlin.plugin.allopen") version "1.5.21"
}

allOpen {
    annotations("javax.persistence.Entity", "javax.persistence.MappedSuperclass", "javax.persistence.Embedabble")
}

group = "me.max"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.2.30")
    implementation("org.hibernate:hibernate-core:5.3.2")
    implementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation("com.h2database:h2:2.1.210")
    implementation("org.testng:testng:7.5")
    implementation("io.kotest:kotest-runner-junit5-jvm:4.6.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.6")
    testImplementation("io.mockk:mockk:1.12.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.5")
    implementation("org.springframework.data:spring-data-jdbc:2.3.3")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.7.0")
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
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.6.1")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.77.Final:osx-x86_64")
    implementation("io.netty:netty-tcnative-boringssl-static:2.0.52.Final")
    implementation("org.postgresql:postgresql:42.3.5")
    implementation("khttp:khttp:1.0.0")
    implementation("org.springframework.boot:spring-boot-starter-mail:2.7.0")
    implementation("org.springframework.boot:spring-boot-starter-activemq:2.7.0")
    implementation("org.springframework:spring-jms:5.3.20")
    implementation("org.apache.activemq:activemq-broker:5.17.1")
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