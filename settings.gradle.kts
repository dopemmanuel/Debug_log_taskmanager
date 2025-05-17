/**
pluginManagement {
    plugins {
        kotlin("jvm") version "2.1.20"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "Debug_log_taskmanager"
*/


pluginManagement {

    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version "2.1.20" // Versión estable más reciente (corregido de 2.1.20)
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "Debug_log_taskmanager"