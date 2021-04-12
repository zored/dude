plugins {
    java
    id("org.jetbrains.intellij") version "0.7.2" // https://plugins.gradle.org/plugin/org.jetbrains.intellij
    kotlin("jvm") version "1.5.0-M2" // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
    id("com.palantir.git-version") version "0.12.3" // https://plugins.gradle.org/plugin/com.palantir.git-version
}

val gitVersion: groovy.lang.Closure<*> by extra

group = "gl.ro"
version = gitVersion()

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    testImplementation("junit", "junit", "4.13") // https://mvnrepository.com/artifact/junit/junit
    testImplementation("pl.pragmatists", "JUnitParams", "1.1.1") // https://github.com/Pragmatists/JUnitParams
}

intellij {
    version = "2021.1"
    type = "IU"
    setPlugins(
        // https://plugins.jetbrains.com/plugin/9568-go/versions
        "org.jetbrains.plugins.go:211.6693.111",

        // https://plugins.jetbrains.com/plugin/13029-index-viewer/versions
        // "com.jetbrains.hackathon.indices.viewer:1.16",

        "java"
    )
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
