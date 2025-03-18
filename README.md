# Test Todo-app

## Overview

- Java: 21
- Spring Boot: 3.4.3
- Allure: 2.30.0
- Gradle: 8.10

## Setup and Deployment

To run the application locally, follow these steps:

1. Load the Docker image:
   ```bash
   docker load < todo-app.tar
2. Run the container using the following command:
   ```bash
   docker run -p 8080:4242 --name todo-app -e VERBOSE=1 todo-app:latest

## Project Configuration

The project is written using Spring Boot to simplify dependency injection and dependency management. The annotation `@SpringJUnitConfig` let us use reduced spring context and compine different components of the project. The parameter `spring.profiles.active` let us pass different parameters to test system locally and remote on server (CICD).

## Testing

Every test-class contains `checklist` - the list of test-cases which could be realized in the future.

To speed up the testing process, test parallelization is enabled

1. To run the tests and generate the Allure report, use:
   ```bash
   gradle test -PspringProfile=local allureServe