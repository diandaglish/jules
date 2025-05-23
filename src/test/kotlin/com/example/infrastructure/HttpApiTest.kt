package com.example.infrastructure

import com.example.domain.Greeting
import com.example.domain.GreetingService
import com.example.domain.LocalizedTime // New import
import com.example.domain.TimeService   // New import
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.core.Response // Ensure this is imported
import org.http4k.format.Jackson.auto // New import for JSON lens
import org.http4k.strikt.bodyString
import org.http4k.strikt.status
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.isEqualTo
import java.time.ZoneId        // For constructing expected time
import java.time.ZonedDateTime  // For constructing expected time
import java.time.format.DateTimeFormatter // For constructing expected time

// Test double for GreetingService (remains the same)
class TestGreetingService(private val greeting: Greeting) : GreetingService {
    override fun getGreeting(): Greeting = greeting
}

// New Test double for TimeService
class TestTimeService(private val response: LocalizedTime?) : TimeService {
    var lastCalledWith: String? = null
    override fun getCurrentTimeForCountryCode(countryCode: String): LocalizedTime? {
        lastCalledWith = countryCode
        return response
    }
}

class HttpApiTest {

    private val testGreeting = Greeting("Hello from Test Service!")
    private val greetingService = TestGreetingService(testGreeting)
    
    // For successful time tests
    private val parisTime = LocalizedTime("Europe/Paris", ZonedDateTime.now(ZoneId.of("Europe/Paris")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
    private val timeServiceSuccess = TestTimeService(parisTime)

    // For time service returning null (country not found)
    private val timeServiceNotFound = TestTimeService(null)

    // App instance for greeting tests
    private val httpAppForGreeting = createHttpApi(greetingService, TestTimeService(null)) // Pass a default TimeService

    @Test
    fun `hello endpoint returns greeting from service`() {
        val request = Request(Method.GET, "/hello")
        val response = httpAppForGreeting(request)
        expectThat(response).status.isEqualTo(Status.OK)
        expectThat(response).bodyString.isEqualTo("Hello from Test Service!")
    }

    // New tests for the /time/{countryCode} endpoint
    @Test
    fun `time endpoint returns localized time for valid country code`() {
        val httpAppWithTime = createHttpApi(greetingService, timeServiceSuccess) // Use TimeService that returns data
        val request = Request(Method.GET, "/time/FR")
        val response = httpAppWithTime(request)

        expectThat(response).status.isEqualTo(Status.OK)
        // Check JSON response (requires Jackson and lens setup in HttpApi.kt)
        val localizedTimeLens = LocalizedTime::class.auto<Response>().toLens()
        val actualLocalizedTime = localizedTimeLens(response) // Extract object from response
        
        expectThat(actualLocalizedTime.timeZone).isEqualTo("Europe/Paris")
        // Compare date part and hour, minute as time is dynamic
        expectThat(actualLocalizedTime.currentTime.substringBeforeLast(":"))
            .isEqualTo(parisTime.currentTime.substringBeforeLast(":"))
    }

    @Test
    fun `time endpoint returns 404 for unsupported country code`() {
        val httpAppWithTimeNotFound = createHttpApi(greetingService, timeServiceNotFound) // Use TimeService that returns null
        val request = Request(Method.GET, "/time/XX")
        val response = httpAppWithTimeNotFound(request)

        expectThat(response).status.isEqualTo(Status.NOT_FOUND)
        expectThat(response).bodyString.isEqualTo("Country code not supported or invalid.")
    }
}
