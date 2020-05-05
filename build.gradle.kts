plugins {
    id("org.jetbrains.intellij") version "0.4.16"
    java
    kotlin("jvm") version "1.3.70"
    kotlin("plugin.serialization") version "1.3.70"
}


group = "gl.ro"
version = "1.0.1"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib", "1.3.70"))
    testCompile("junit", "junit", "4.12")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
}

intellij {
    version = "2020.1.1"
    type = "IU"
    setPlugins(
        "org.jetbrains.plugins.go:201.7223.8.147",
        "PsiViewer:201.6251.22-EAP-SNAPSHOT.3",
        "com.jetbrains.hackathon.indices.viewer:1.4"
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

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("First version is here.")
}