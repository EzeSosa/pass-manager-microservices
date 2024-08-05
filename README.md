# Password Manager Backend - Microservices

Microservices version of Password Manager application, which allows users to generate and manage robust and random passwords to ensure their accounts.

## Overview

Technologies used:
  - Java
  - Spring Boot
  - Spring Cloud
  - Spring Data JPA
  - Spring Security
  - MySQL
  - Eureka Server
  - Zipkin

## Architecture

![imagen](https://github.com/user-attachments/assets/881b6b16-e74d-4c4d-b70b-4d38fd809bd1)

### API Gateway

Used to redirect HTTP requests to the services based on the request path. It's also configured to allow CORS to accept requests from the front-end.

### Services

The application has three services:

  - Auth Service, to manage authentication and to secure the endpoints
  - User Service, to fetch the user information
  - Password Service, to manage an user's passwords

User Service and Password Service have their own MySQL database. Each service communicate with each other via HTTP using WebClient to synchronize and to validate information.

### Config Server

Used to store the configuration of each service via .yaml files. Services retrieve this information when they start. 

### Service Registry

Stores the location of all running services at a central point so the services can retrieve information about other services. For this application, Eureka Netflix service registry was used.

### Tracing

To trace all the services requests and communications, it was used [Zipkin](https://zipkin.io/). Each service has the corresponding dependency.

## Installation

First, to get the project:

  1. Clone the repository: `git clone https://github.com/EzeSosa/pass-manager-microservices.git`
  2. Navigate to the project directory: `cd your-repo`

Then, for each directory:

  3. Build the project using Maven: `mvn clean install`
  4. Run the project: `mvn spring-boot:run`
  5. The API gateway will run at `http://localhost:9000`. You can use either the [front-end page](https://github.com/EzeSosa/pass-manager-front) or Postman to start generating passwords. If you use the front-end, make sure to switch to the microservices branch.

Notice that since we are using MySQL, for User and Password Service, you must create your own database and configure it on each application.yaml file.
