# http4k Onion Architecture Microservice

A simple microservice built with http4k demonstrating the Onion Architecture pattern.

## Requirements

- JDK 1.8 or higher

## Building the Project

To build the project and download all dependencies, run the following command from the project root:

```bash
./gradlew build
```

This will compile the source code and run the tests.

## Running the Application

To run the application, execute the following command from the project root:

```bash
./gradlew run
```

The server will start on port 9000. You can access the main endpoint at:
[http://localhost:9000/hello](http://localhost:9000/hello)

This should return a message like: "Hello, World from Onion Architecture!"

## Running Tests

To execute the unit tests, run:

```bash
./gradlew test
```

Test reports can be found in `build/reports/tests/test/index.html`.

## Project Structure

This project follows the Onion Architecture to promote separation of concerns, testability, and maintainability.

-   **`src/main/kotlin/com/example/domain`**: Contains the core business logic and entities.
    -   `Greeting.kt`: Defines the `Greeting` data class.
    -   `GreetingService.kt`: Defines the `GreetingService` interface.
-   **`src/main/kotlin/com/example/application`**: Contains the application services that orchestrate the domain logic.
    -   `GreetingServiceImpl.kt`: Implements the `GreetingService`.
-   **`src/main/kotlin/com/example/infrastructure`**: Contains the adapters to the outside world (e.g., HTTP API, database access).
    -   `HttpApi.kt`: Defines the http4k routes and handlers.
-   **`src/main/kotlin/com/example/Main.kt`**: The main entry point of the application, responsible for dependency injection (composition root) and starting the server.
-   **`src/test/kotlin/`**: Contains the unit and integration tests for the respective layers.
