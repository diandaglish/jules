package com.example

import com.example.application.GreetingServiceImpl
import com.example.infrastructure.createHttpApi
import org.http4k.server.Netty
import org.http4k.server.asServer

fun main() {
    // Instantiate services
    val greetingService = GreetingServiceImpl()

    // Create HttpApi (routes) with injected services
    val httpApi = createHttpApi(greetingService)

    // Start the server
    httpApi.asServer(Netty(9000)).start()
    println("Server started on port 9000 with Onion Architecture")
}
