package com.example.infrastructure

import com.example.domain.GreetingService
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes

fun createHttpApi(greetingService: GreetingService): org.http4k.core.HttpHandler {
    return routes(
        "/hello" bind Method.GET to {
            val greeting = greetingService.getGreeting()
            Response(Status.OK).body(greeting.message)
        }
    )
}
