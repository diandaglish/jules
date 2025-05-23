package com.example

import com.example.application.GreetingServiceImpl
import com.example.application.TimeServiceImpl // Import for TimeServiceImpl
import com.example.infrastructure.createHttpApi
import org.http4k.server.Netty
import org.http4k.server.asServer

fun main() {
    // Instantiate services
    val greetingService = GreetingServiceImpl()
    val timeService = TimeServiceImpl() // Instantiate TimeServiceImpl

    // Create HttpApi (routes) with injected services
    // Update the call to pass both services
    val httpApi = createHttpApi(greetingService, timeService) 

    // Start the server
    httpApi.asServer(Netty(9000)).start()
    println("Server started on port 9000 with Onion Architecture. Try /hello or /time/FR")
}
