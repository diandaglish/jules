package com.example.application

import com.example.domain.Greeting
import com.example.domain.GreetingService

class GreetingServiceImpl : GreetingService {
    override fun getGreeting(): Greeting {
        return Greeting("Hello, World from Onion Architecture!")
    }
}
