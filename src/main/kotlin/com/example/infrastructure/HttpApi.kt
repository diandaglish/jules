package com.example.infrastructure

import com.example.domain.GreetingService
import com.example.domain.LocalizedTime
import com.example.domain.TimeService
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Jackson.auto // Import for Jackson auto marshalling
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes

// Update the function to accept TimeService
fun createHttpApi(greetingService: GreetingService, timeService: TimeService): HttpHandler {
    // Define the lens for LocalizedTime once
    val localizedTimeLens = LocalizedTime::class.auto<Response>().toLens()

    return routes(
        "/hello" bind Method.GET to {
            val greeting = greetingService.getGreeting()
            Response(Status.OK).body(greeting.message)
        },

        "/time/{countryCode}" bind Method.GET to { request ->
            val countryCode = request.path("countryCode")
            if (countryCode == null) {
                Response(Status.BAD_REQUEST).body("Country code path parameter is missing.")
            } else {
                val localizedTime = timeService.getCurrentTimeForCountryCode(countryCode)
                if (localizedTime != null) {
                    // Use the lens to set the body with the LocalizedTime object
                    Response(Status.OK).with(localizedTimeLens of localizedTime)
                } else {
                    Response(Status.NOT_FOUND).body("Country code not supported or invalid.")
                }
            }
        }
    )
}
