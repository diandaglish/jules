package com.example

import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer

fun main() {
    val app: HttpHandler = routes(
        "/hello" bind Method.GET to {
            Response(Status.OK).body("Hello, World!")
        }
    )

    app.asServer(Netty(9000)).start()
    println("Server started on port 9000")
}
