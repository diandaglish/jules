package com.example.application

import com.example.domain.Greeting
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class GreetingServiceImplTest {

    @Test
    fun `getGreeting returns correct message`() {
        // Arrange
        val service = GreetingServiceImpl()

        // Act
        val greeting = service.getGreeting()

        // Assert
        expectThat(greeting).isEqualTo(Greeting("Hello, World from Onion Architecture!"))
    }
}
