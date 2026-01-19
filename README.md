# Renault Garage Management Microservice

This microservice manages Renault garage network information including garages, vehicles, and accessories using **Hexagonal Architecture**.

## Requirements

- Java 17
- Maven
- Docker & Docker Compose

## Running the Application

1. Start infrastructure services (PostgreSQL, Kafka):
   ```bash
   docker-compose up -d
   ```

2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Documentation

- OpenAPI UI: http://localhost:8080/swagger-ui.html
