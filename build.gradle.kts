plugins {
    kotlin("jvm") version "2.2.0"
    application
}

group = "com.realex"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
}

kotlin {
    jvmToolchain(21)
}

application {
    mainClass.set("com.realex.ladder.MainKt")
}

tasks.test {
    useJUnitPlatform()
}
