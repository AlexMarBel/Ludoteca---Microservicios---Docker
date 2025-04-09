## Overview
This project is a microservices-based application for managing a game library. It allows users to manage authors, games, categories, customers, and loans. The application is built with **Angular** on the frontend and **Java Spring Boot** on the backend, and it is containerized using **Docker** and orchestrated with **Docker Compose**.

## Features
- **Frontend**: Built with Angular, providing a user-friendly interface for managing the game library.
- **Backend**: Developed with Spring Boot, with each microservice handling a specific domain (e.g., authors, games, categories, customers, loans).
- **Service Discovery**: Uses Eureka for service registration and discovery.
- **API Gateway**: Spring Cloud Gateway is used to route requests to the appropriate microservices.
- **Health Monitoring**: Each service includes health checks for monitoring.
- **Network Isolation**: Services are organized into separate networks (`frontend-network`, `backend-network`, and `admin-network`) for better isolation and security.
- **Simulated Agents**: Includes `cliente-agent` and `admin-agent` to simulate client and admin interactions with the services.
