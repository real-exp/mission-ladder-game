plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.spring") version "2.2.0"
    id("org.springframework.boot") version "3.5.16"
    id("io.spring.dependency-management") version "1.1.7"
    application
}

val WEB_MAIN_CLASS = "com.realex.ladder.LadderApplicationKt"

group = "com.realex"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation("io.kotest:kotest-runner-junit5:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

kotlin {
    jvmToolchain(21)
}

// 콘솔과 웹 두 진입점이 있다. gradle run 은 콘솔, gradle bootRun 은 웹이다.
application {
    mainClass.set("com.realex.ladder.MainKt")
}

// application 플러그인의 mainClass 가 boot 태스크까지 먹으므로 웹 진입점을 따로 못 박는다
tasks.bootJar {
    mainClass.set(WEB_MAIN_CLASS)
}

tasks.bootRun {
    mainClass.set(WEB_MAIN_CLASS)
}

tasks.test {
    useJUnitPlatform()
}

// 콘솔로 입력을 받으므로 gradle run 에 터미널 stdin 을 연결한다
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
