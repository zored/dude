plugins {
    java
    id("org.jetbrains.intellij") version "1.1.5" // https://plugins.gradle.org/plugin/org.jetbrains.intellij
    kotlin("jvm") version "1.5.31" // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
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
    testImplementation("junit", "junit", "4.13.2") // https://mvnrepository.com/artifact/junit/junit
    testImplementation("pl.pragmatists", "JUnitParams", "1.1.1") // https://github.com/Pragmatists/JUnitParams
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect
    implementation(platform("org.jetbrains.kotlin:kotlin-reflect:1.5.10"))
}

// https://plugins.jetbrains.com/docs/intellij/build-number-ranges.html#intellij-platform-based-products-of-recent-ide-versions
intellij {
    version.set("2021.2.3")
    type.set("IU")
    plugins.set(
        listOf(
            // https://plugins.jetbrains.com/plugin/9568-go/versions
            "org.jetbrains.plugins.go:212.5457.20",
            "java"
        )
    )
}

tasks.wrapper {
    gradleVersion = "6.6.1"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
