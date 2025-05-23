package com.example.infrastructure

import com.example.domain.Greeting
import com.example.domain.GreetingService
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import java.util.Currency
import java.util.Locale

// A test double for GreetingService
class TestGreetingService(private val greeting: Greeting) : GreetingService {
    override fun getGreeting(): Greeting = greeting
}

class HttpApiTest {

    @Test
    fun `hello endpoint returns greeting from service`() {
        // Arrange: Use the test double
        val testGreeting = Greeting("Hello from Test Service!")
        val greetingService = TestGreetingService(testGreeting)
        val httpApp = createHttpApi(greetingService) // Inject the test double

        val request = Request(Method.GET, "/hello")

        // Act
        val response = httpApp(request)

        // Assert
        expectThat(response).status.isEqualTo(Status.OK)
        expectThat(response).bodyString.isEqualTo("Hello from Test Service!")
    }

    @Test
    fun `currency endpoint returns system currency`() {
        // Arrange
        val testGreeting = Greeting("Test") // Not used by /currency, but createHttpApi needs it
        val greetingService = TestGreetingService(testGreeting)
        val httpApp = createHttpApi(greetingService)
        val request = Request(Method.GET, "/currency")

        // Act
        val response = httpApp(request)

        // Assert
        expectThat(response).status.isEqualTo(Status.OK)
        val expectedCurrencyCode = Currency.getInstance(Locale.getDefault()).currencyCode
        expectThat(response).bodyString.isEqualTo(expectedCurrencyCode)
    }
}
