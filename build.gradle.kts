plugins {
    kotlin("jvm") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.10"
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.flekkio.playeranking"
version = "1.0"

application {
    mainClass.set("com.flekkio.playeranking.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    val ktorVersion = "2.3.4"

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("org.litote.kmongo:kmongo-coroutine:4.9.0")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    testImplementation(kotlin("test"))
}