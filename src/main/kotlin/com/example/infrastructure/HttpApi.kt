package com.example.infrastructure

import com.example.domain.GreetingService
import java.util.Currency
import java.util.Locale
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
        },
        "/currency" bind Method.GET to {
            val locale = Locale.getDefault()
            val currency = Currency.getInstance(locale)
            val currencyCode = currency.currencyCode
            Response(Status.OK).body(currencyCode)
        }
    )
}
