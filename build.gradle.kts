plugins {
    id("org.jetbrains.intellij") version "0.4.19"
    java
    kotlin("jvm") version "1.3.70"
    id("com.palantir.git-version") version "0.12.3"
}


group = "gl.ro"
val gitVersion: groovy.lang.Closure<*> by extra
version = gitVersion()

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib", "1.3.70"))
    testCompile("junit", "junit", "4.12")
    testCompile(kotlin("stdlib-jdk8", "1.3.70"))
}

intellij {
    version = "2020.1.1"
    type = "IU"
    setPlugins(
        "org.jetbrains.plugins.go:201.7223.82.165",
        "com.jetbrains.hackathon.indices.viewer:1.4",
        "java"
    )
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
