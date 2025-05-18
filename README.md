# ProductStore

ProductStore is a microservices-based application designed to manage products, clients, and sales. It leverages a combination of Java Spring Boot services and a Next.js frontend, orchestrated using Docker Compose for seamless development and deployment.

## 🧱 Project Structure

* **product-service**: Handles product-related operations.
* **client-service**: Manages client information and interactions.
* **sale-service**: Processes sales transactions and related logic.
* **next-products-app**: Frontend application built with Next.js.
* **docker-compose.yml**: Defines and manages multi-container Docker applications.

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed on your system:

* [Docker](https://www.docker.com/get-started)
* [Docker Compose](https://docs.docker.com/compose/install/)

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/ChrystianMSC/ProductStore.git
   cd ProductStore
   ```

2. **Build and start the services:**

   ```bash
   docker-compose up --build
   ```

This command will build the Docker images and start all the services defined in the `docker-compose.yml` file.

Them you can move to the next-products-app directory in another terminal and run:

   ```bash
   npm i
   npm run dev
   ```

3. **Access the application:**

   * Frontend (Next.js app): [http://localhost:3000](http://localhost:3000)
   * Product Service API: [http://localhost:8081](http://localhost:8081)
   * Client Service API: [http://localhost:8082](http://localhost:8082)
   * Sale Service API: [http://localhost:8083](http://localhost:8083)

   Ensure these ports are not occupied by other applications.

## 🛠️ Development

To work on individual services:

1. **Navigate to the desired service directory:**

   ```bash
   cd product-service
   ```

2. **Run the service locally:**

   ```bash
   ./mvnw spring-boot:run
   ```

Repeat the above steps for `client-service` and `sale-service` as needed.

3. **For the frontend application:**

   ```bash
   cd next-products-app
   npm install
   npm run dev
   ```

This will start the Next.js development server on [http://localhost:3000](http://localhost:3000).

## 🧪 Testing

Each service includes its own set of tests. To run tests for a specific service:

1. **Navigate to the service directory:**

   ```bash
   cd product-service
   ```

2. **Execute the tests:**

   ```bash
   ./mvnw test
   ```

Repeat for other services as needed.
