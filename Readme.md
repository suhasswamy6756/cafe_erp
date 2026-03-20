# Cafe ERP

A production-ready Enterprise Resource Planning (ERP) system built with Spring Boot, designed specifically for cafe and restaurant operations. It covers the full business lifecycle — from procurement and inventory to point-of-sale transactions — with multi-location support and role-based access control.

---

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Getting Started (Local Development)](#getting-started-local-development)
- [Configuration Reference](#configuration-reference)
- [API Overview](#api-overview)
- [Database & Migrations](#database--migrations)
- [Running Tests](#running-tests)
- [Docker](#docker)
- [Deployment](#deployment)
- [Monitoring](#monitoring)

---

## Features

| Module | Capabilities |
|---|---|
| **Authentication** | JWT-based login/logout, user registration, role-based access control |
| **Catalogue** | Menu items, categories, time-based availability, modifier groups, charges, discounts, taxes |
| **Inventory** | Raw materials, stock tracking, stock transfers, manual adjustments |
| **Procurement** | Supplier management, purchase orders, goods receipt notes (GRN) with rejection tracking |
| **Recipes** | Multi-version recipe management, ingredient tracking, cost auditing |
| **Point of Sale** | Order creation, item modifications, checkout, payment processing, order cancellation |
| **Admin** | Multi-location management with schedules, charges, and per-location tax configuration |
| **Monitoring** | Spring Boot Actuator health endpoint, Prometheus metrics export |

---

## Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.6 |
| Build Tool | Apache Maven 4 |
| Database | PostgreSQL |
| ORM | Spring Data JPA (Hibernate 6) |
| Migrations | Flyway 11.15.0 |
| Security | Spring Security + JWT (JJWT 0.12.6) |
| API Docs | SpringDoc OpenAPI 2.8.13 (Swagger UI) |
| Serialization | Jackson + Hibernate 6 data type |
| Code Generation | Lombok |
| Monitoring | Spring Boot Actuator + Micrometer Prometheus |
| Container | Docker (eclipse-temurin:17-jdk-alpine) |
| Cloud | AWS ECS + ECR (eu-north-1) |
| CI/CD | Jenkins, GitHub Actions |

---

## Project Structure

```
cafe_erp/
├── src/
│   ├── main/
│   │   ├── java/com/cafe/erp/
│   │   │   ├── ErpApplication.java          # Application entry point
│   │   │   ├── common/
│   │   │   │   ├── config/                  # Security, JWT, JPA auditing, Jackson
│   │   │   │   ├── enums/                   # Shared business enumerations
│   │   │   │   ├── exception/               # Global exception handler + custom exceptions
│   │   │   │   ├── model/                   # BaseEntity (audit fields)
│   │   │   │   └── utils/                   # Utility helpers
│   │   │   └── modules/
│   │   │       ├── admin/location/          # Locations, schedules, charges, taxes
│   │   │       ├── authorization/           # Baristas, roles, user-roles, auth
│   │   │       ├── catalogue/               # Items, categories, modifiers, discounts
│   │   │       ├── inventory/               # Materials, suppliers, purchase, GRN, stock, recipes
│   │   │       └── pos/order/               # POS orders and consumption
│   │   └── resources/
│   │       ├── application.properties       # Base configuration
│   │       ├── application-local.properties # Local development overrides
│   │       ├── application-prod.properties  # Production overrides
│   │       └── db/migration/                # Flyway SQL migration scripts
│   └── test/
│       └── java/                            # Unit / integration tests
├── Dockerfile                               # Container image definition
├── Jenkinsfile                              # Jenkins CI/CD pipeline
├── .github/workflows/deploy.yml             # GitHub Actions workflow
└── pom.xml                                  # Maven project descriptor
```

All entities extend `BaseEntity`, which provides soft-delete support (`is_deleted`, `deleted_at`) and full audit trails (`created_at`, `created_by`, `updated_at`, `updated_by`, `deleted_by`).

---

## Prerequisites

- **Java 17** — [Download Temurin](https://adoptium.net/)
- **Maven 3.9+** (or use the bundled `./mvnw` wrapper)
- **PostgreSQL 14+** — running locally on port `5432`
- **Docker** (optional, for containerised local runs)

---

## Getting Started (Local Development)

### 1. Clone the repository

```bash
git clone https://github.com/suhasswamy6756/cafe_erp.git
cd cafe_erp
```

### 2. Create the local database

```sql
CREATE DATABASE cafe_erp_db;
```

The default credentials expected by the `local` profile are `postgres / postgres`. Adjust `application-local.properties` if your setup differs.

### 3. Run database migrations

Flyway migrations are located in `src/main/resources/db/migration/`. They run automatically on startup when `spring.flyway.enabled=true`. For local development the migrations are currently disabled by default — you can enable them by adding the following to `application-local.properties`:

```properties
spring.flyway.enabled=true
```

### 4. Start the application

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

The server starts on **http://localhost:8000**.

### 5. Explore the API

Interactive Swagger UI is available at:

```
http://localhost:8000/swagger-ui/index.html
```

OpenAPI JSON spec:

```
http://localhost:8000/v3/api-docs
```

---

## Configuration Reference

| Property | Default / Local | Production |
|---|---|---|
| `server.port` | `8000` | `8000` |
| `spring.profiles.default` | `local` | set via `SPRING_PROFILES_ACTIVE=prod` |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/cafe_erp_db` | AWS RDS endpoint |
| `spring.datasource.username` | `postgres` | `postgres` |
| `spring.jpa.show-sql` | `true` | `false` |
| `spring.flyway.enabled` | `false` | `false` |
| `logging.level.root` | `DEBUG` | `DEBUG` |
| `jwt.secret` | base64-encoded secret | same key (rotate for production) |
| `management.endpoints.web.exposure.include` | `health,prometheus` | `health,prometheus` |

> **Security note:** Never commit real credentials. Override sensitive values using environment variables or a secrets manager in production.

---

## API Overview

All endpoints (except login and registration) require a valid **Bearer JWT token** in the `Authorization` header.

### Authentication

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/login` | Barista login, returns JWT |
| `POST` | `/api/register` | Register a new user |
| `POST` | `/api/logout` | Invalidate the current token |
| `GET` | `/api/baristas` | List all staff |

### Catalogue

| Method | Path | Description |
|---|---|---|
| `GET/POST` | `/api/items` | List / create menu items |
| `PUT/DELETE` | `/api/items/{id}` | Update / delete a menu item |
| `GET/POST` | `/api/categories` | List / create categories |
| `GET/POST` | `/api/category-schedules` | Manage time-based availability |
| `GET/POST` | `/api/modifier-groups` | Manage modifier groups |
| `GET/POST` | `/api/charges` | Manage additional charges |
| `GET/POST` | `/api/discounts` | Manage discounts |
| `GET/POST` | `/api/taxes` | Manage tax configurations |

### Inventory

| Method | Path | Description |
|---|---|---|
| `GET/POST` | `/api/inventory/materials` | Manage raw materials |
| `GET/POST` | `/api/inventory/suppliers` | Manage suppliers |
| `GET/POST` | `/api/inventory/purchase` | Manage purchase orders |
| `GET/POST` | `/api/inventory/grn` | Goods receipt notes |
| `GET/POST` | `/api/inventory/stock` | Stock levels |
| `GET/POST` | `/api/inventory/stock-transfer` | Inter-location stock transfers |
| `GET/POST` | `/api/inventory/stock-adjustments` | Manual stock corrections |
| `GET/POST` | `/api/inventory/recipes` | Recipe management and costing |

### Point of Sale

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/pos/orders` | Create a new order |
| `PUT` | `/api/pos/orders/{id}/items` | Add / update items on an order |
| `POST` | `/api/pos/orders/{id}/checkout` | Checkout and process payment |
| `POST` | `/api/pos/orders/{id}/cancel` | Cancel an order |

### Administration

| Method | Path | Description |
|---|---|---|
| `GET/POST` | `/api/locations` | Manage cafe locations |

### System

| Path | Description |
|---|---|
| `/actuator/health` | Application health status |
| `/actuator/prometheus` | Prometheus metrics scrape endpoint |

---

## Database & Migrations

Database schema changes are managed with **Flyway**. Migration scripts live in:

```
src/main/resources/db/migration/
```

Scripts follow the naming convention `V{version}__{description}.sql`. Current migrations:

| Version | Description |
|---|---|
| V2 | Drop barista audit logs |
| V3 | Drop barista audit trigger |
| V4 | Create categories and timings tables |
| V5 | Alter category timings table |
| V6 | Add category schedules |
| V7 | Create modifier groups and options |

To apply migrations manually:

```bash
./mvnw flyway:migrate -Dspring-boot.run.profiles=local
```

---

## Running Tests

```bash
./mvnw test
```

---

## Docker

### Build the image

```bash
./mvnw clean package -DskipTests
docker build -t cafe-erp-backend .
```

### Run the container

```bash
docker run -p 8000:8000 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/appdb \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=<password> \
  cafe-erp-backend
```

The container exposes port `8000` and defaults to the `prod` Spring profile.

---

## Deployment

### GitHub Actions

The workflow defined in `.github/workflows/deploy.yml` is triggered on Git tags matching `v*`. It:

1. Builds the JAR (`mvn clean package -DskipTests`)
2. Copies the artifact to the target EC2 instance via SCP
3. Restarts the application service on the instance

### Jenkins

The `Jenkinsfile` defines a pipeline that:

1. **Checks out** source code
2. **Builds** the JAR
3. **Builds** a Docker image and tags it with the Jenkins build number
4. **Logs in** to AWS ECR (`eu-north-1`)
5. **Pushes** the image to ECR
6. **Registers** a new ECS task definition revision with the updated image
7. **Updates** the ECS service (`cafe-erp-cluster-test`) to deploy the new revision

Required Jenkins environment variables / credentials:

| Variable | Description |
|---|---|
| `AWS_REGION` | `eu-north-1` |
| `ECR_REPO` | Full ECR repository URI |
| `CLUSTER` | ECS cluster name |
| `SERVICE` | ECS service name |
| `TASK_DEF_NAME` | ECS task definition family name |

---

## Monitoring

| Endpoint | Description |
|---|---|
| `GET /actuator/health` | JSON health status (database, disk, etc.) |
| `GET /actuator/prometheus` | Prometheus-format metrics for scraping |

Configure a Prometheus scrape job pointing to `/actuator/prometheus` on port `8000` to collect JVM, HTTP, and custom application metrics.
