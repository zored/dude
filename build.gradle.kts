plugins {
    id("org.jetbrains.intellij") version "0.4.16"
    java
    kotlin("jvm") version "1.3.61"
}

group = "gl.ro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2019.3.3"
    type = "IU"
    setPlugins(
        "org.jetbrains.plugins.go:193.6494.35.125",
        "PsiViewer:193-SNAPSHOT", // todo
        "com.jetbrains.hackathon.indices.viewer:1.3" // todo
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
    // todo:
//    runIde {
//        setIdeDirectory("/Users/r.akhmerov/Library/Application Support/JetBrains/Toolbox/apps/Goland/ch-0/193.6494.61/GoLand.app/Contents/")
//    }
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("First version is here.")
}