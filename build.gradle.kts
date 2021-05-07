import kotlinx.coroutines.internal.artificialFrame

plugins {
    java
    id("org.jetbrains.intellij") version "0.7.2" // https://plugins.gradle.org/plugin/org.jetbrains.intellij
    kotlin("jvm") version "1.5.0-RC" // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
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
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect
    implementation(platform("org.jetbrains.kotlin:kotlin-reflect:1.5.0-RC"))
}

intellij {
    version = "2020.3.4"
    type = "IU"
    setPlugins(
        // https://plugins.jetbrains.com/plugin/9568-go/versions
        "org.jetbrains.plugins.go:203.6682.168",
        "java"
    )
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
