# PlatformE

PlatformE is a small e-commerce backend exposing a product catalog, a customer
checkout flow, and an admin area to manage products, inventory, and orders.

It is a Spring Boot application packaged as an AWS Lambda function (serverless,
via [Poja](https://poja.io) and the AWS Serverless Java Container).

## Features

- **Product catalog** – paginated, keyword-filtered product listing and a
  "new arrivals" feed (products created in the last 7 days).
- **Checkout** – creates a `PENDING` order, decrements stock atomically
  (pessimistic locking) and computes the total from current prices.
- **Order lifecycle** – `PENDING → PAID → SHIPPED`, and cancellation
  (`PENDING`/`PAID → CANCELLED`) which restores stock.
- **Inventory management** – restock & revalue a product; adding more than 100
  units applies a 10% discount on the new base price.
- **Domain events** – when a product runs out of stock an async email is sent to
  the admin; when a product is back in stock an async handler is notified.
- **Health checks** – database, file bucket, and email connectivity.
- **Security** – HTTP Basic authentication; the `/admin/**` area requires the
  `ADMIN` role.

## Tech stack

| Concern        | Choice                                                       |
| -------------- | ------------------------------------------------------------ |
| Language       | Java 21                                                      |
| Framework      | Spring Boot 3.2.2 (Web, Data JPA, Security, Validation)      |
| Persistence    | PostgreSQL + Flyway, Hibernate                               |
| AWS            | Lambda, S3, SES, SQS, EventBridge (SDK v2)                   |
| Security       | Spring Security, HTTP Basic, BCrypt                          |
| API spec       | OpenAPI 3.0 (`doc/api.yml`)                                  |
| Build          | Gradle (wrapper 8.5)                                         |
| Testing        | JUnit 5, Testcontainers (PostgreSQL), JaCoCo                 |
| Boilerplate    | Lombok                                                       |

## Architecture

The same Spring Boot application runs in two ways:

- **Locally** as a regular embedded-server app (`./gradlew bootRun`).
- **On AWS** as a Lambda function handled by `LambdaHandler`
  (`src/main/java/.../handler/LambdaHandler.java`), which forwards HTTP API
  (payload v2) events into the Spring context using
  `SpringBootLambdaContainerHandler`.

Both a database and the AWS services (S3/SES) must be reachable at runtime.

### Order lifecycle

```
PENDING ──pay──▶ PAID ──ship──▶ SHIPPED
   │              │
   └────cancel────┴────▶ CANCELLED
```

- `pay` is only allowed from `PENDING`.
- `ship` is only allowed from `PAID`.
- `cancel` is rejected once the order is `SHIPPED` or `CANCELLED`; otherwise the
  stock of every line is restored.

## API

The full specification lives in [`doc/api.yml`](doc/api.yml) (OpenAPI 3.0).
Base URL: `http://localhost:8080`.

### Public / health

| Method | Path                    | Description                              |
| ------ | ----------------------- | ---------------------------------------- |
| GET    | `/ping`                 | Liveness ping, returns `pong`.           |
| GET    | `/health/db`            | `OK` when the dummy table is not empty.  |
| GET    | `/health/bucket`        | Uploads/downloads/presigns a test file.  |
| GET    | `/health/email?to=`     | Sends a test email.                      |
| GET    | `/products`             | Paginated products (`keyword`, `page`, `size`). |
| GET    | `/products/new-arrivals`| Products created in the last 7 days.     |

### Authenticated

| Method | Path                    | Description                              |
| ------ | ----------------------- | ---------------------------------------- |
| POST   | `/orders/checkout`      | Create an order from a cart.             |

### Admin (role `ADMIN`)

| Method | Path                                | Description                                  |
| ------ | ----------------------------------- | -------------------------------------------- |
| POST   | `/admin/products`                   | Create a product.                            |
| PUT    | `/admin/products/{id}`              | Update a product.                            |
| DELETE | `/admin/products/{id}`              | Delete a product.                            |
| PUT    | `/admin/products/{id}/restock`      | Restock & revalue (`addedQuantity`, `newBasePrice`). |
| GET    | `/admin/orders?email=`              | List a customer's orders.                    |
| PUT    | `/admin/orders/{id}/cancel`         | Cancel an order.                             |
| PUT    | `/admin/orders/{id}/pay`            | Mark an order paid.                          |
| PUT    | `/admin/orders/{id}/ship`           | Mark an order shipped.                       |

Example:

```bash
# Public product listing
curl http://localhost:8080/products

# Admin action (HTTP Basic)
curl -u admin:secret -X PUT \
  "http://localhost:8080/admin/products/<id>/restock?addedQuantity=150&newBasePrice=29.99"
```

## Getting started

### Prerequisites

- JDK 21
- Docker (required to run integration tests with Testcontainers)
- A PostgreSQL database (and AWS credentials) for a full local run

### Build

```bash
./gradlew build          # Linux / macOS
gradlew.bat build        # Windows
```

### Run

```bash
./gradlew bootRun
```

### Test

```bash
./gradlew test                       # runs JUnit 5 + Testcontainers
./gradlew test jacocoTestReport      # coverage report in build/reports/jacoco
```

### Format

```bash
./format.sh     # Linux / macOS
format.bat     # Windows
```

## Configuration

There is no bundled `application.properties`; configuration is provided through
environment variables (this is how it is deployed). Relevant keys:

| Key                          | Default             | Purpose                              |
| ---------------------------- | ------------------- | ------------------------------------ |
| `app.admin.username`         | `admin`             | Admin basic-auth username.           |
| `app.admin.password`         | *(empty)*           | Admin basic-auth password (BCrypt-hashed at startup). **Set this.** |
| `app.admin.email`            | `noreply@poja.io`   | Recipient of out-of-stock alerts.    |
| `aws.s3.bucket`              | *(none)*            | Bucket used by the bucket component. |
| `spring.datasource.url`      | *(none)*            | PostgreSQL JDBC URL.                 |
| `spring.datasource.username` | *(none)*            | Database user.                       |
| `spring.datasource.password` | *(none)*            | Database password.                   |

AWS region is fixed to `eu-west-3`.

## Database migrations

Schema is versioned with Flyway under
`src/main/resources/db/migration`:

- `V42_1__Create_dummy_table.sql`
- `V42_2__Create_dummy_uuid_table.sql`
- `V43__initial_schema.sql` – `product`, `orders`, `order_line`
- `V44__add_order_status.sql` – adds `orders.status`

> Migrations that have already been applied must not be modified; add a new
> versioned file instead.

## Project structure

```
src/main/java/com/sombiniaina/project
├── endpoint/rest/controller      # REST controllers (products, orders, inventory, health)
├── endpoint/rest                 # Global exception handler
├── service                       # Business logic + domain event consumers
├── repository                    # Spring Data repositories + JPA entities (repository/model)
├── model                         # Domain records & enums
├── dto/request                   # Request payloads
├── mapper                        # Entity <-> domain mappers
├── security                      # SecurityFilterChain & user details
├── mail / file                    # SES emailer and S3 bucket helpers
├── handler/LambdaHandler.java    # AWS Lambda entry point
└── PojaApplication.java          # Spring Boot entry point
```

## CI / CD

GitHub Actions under `.github/workflows`:

- `ci.yml` – runs tests and a formatting check on every push/PR.
- `cd-compute.yml` – on `preprod`/`prod`, builds the Lambda artifact
  (`sam build`) and hands it to Poja for deployment.
- `release-version.yml` – version/release automation.

Deployment and infrastructure are managed by [Poja](https://poja.io).

## Acknowledgements

Scaffolded from the Poja serverless starter template; classes annotated with
`@PojaGenerated` are generated and may be overwritten on regeneration.
