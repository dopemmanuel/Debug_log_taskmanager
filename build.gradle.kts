plugins {
    kotlin("jvm") version "2.0.20"
}

group = "es.prog2425.taskmanager"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2") // Kotest framework
    testImplementation("io.kotest:kotest-assertions-core:5.6.2") // Aserciones
    testImplementation("io.kotest:kotest-property:5.6.2") // Pruebas basadas en propiedades
    testImplementation("io.mockk:mockk:1.13.7") // Mocking library

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
    }
}
kotlin {
    jvmToolchain(21)
    jvmToolchain(17)
    jvmToolchain(8)
}