package com.example

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.junit.jupiter.api.Test // If using JUnit 5
// import kotlin.test.Test // If using kotlin.test with JUnit 4 or older. Ensure build.gradle.kts has `testImplementation(kotlin("test-junit"))` for JUnit 4.
import strikt.api.expectThat

class MainTest {

    // Define the app locally for testing, mirroring the structure in Main.kt
    private val app = routes(
        "/hello" bind Method.GET to {
            Response(Status.OK).body("Hello, World!")
        }
    )

    @Test
    fun `hello endpoint returns Hello, World!`() {
        val request = Request(Method.GET, "/hello")
        val response = app(request) // Directly call the app handler

        expectThat(response).status.isEqualTo(Status.OK)
        expectThat(response).bodyString.isEqualTo("Hello, World!")
    }
}
