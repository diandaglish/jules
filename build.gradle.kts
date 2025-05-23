plugins {
    kotlin("jvm") version "1.9.22" // Or the latest stable version
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:5.16.0.0")) // Use the latest http4k version
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-netty") // Or your preferred server, e.g., http4k-server-undertow
    testImplementation(platform("org.http4k:http4k-bom:5.16.0.0"))
    testImplementation("org.http4k:http4k-testing-strikt") // Or http4k-testing-hamkrest
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("com.example.MainKt") // Will create this file later
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "21" // Or your preferred JVM target
}
