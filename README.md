# Summary

Summary and explanation of the Kafka example project with producer and consumer developed by [Juan Carlos Guerrero Moyano](https://www.linkedin.com/in/jcguerreromoyano/) - [GITHUB](https://github.com/jcguerrero21). This document will provide a high-level description of the different tools used, along with a brief explanation of the various microservices/artifacts and how to start it if not using any IDE.

## Tech Stack

**Java version:** 21

**Spring boot version:** 3.2.5 & 3.3.0

**Database:** PostgreSQL

**Docker + Docker-compose:** Containerization with Docker

**Plugins:** MapStruct, OpenAPITools Generator, Apache Avro for Schema Registry

**Server:** Spring Boot default localhost

**Client tool default:** Swagger docs [URL (should be the application running)](http://localhost:8080/swagger-ui/index.html)

## Documentation

The technical test consists of 2 microservices/artifacts and a common library.

* **ms-common-core**
The common library contains classes annotated with `@Entity` for different database transactions (PostgreSQL), JPA repository interfaces, and other common classes + dependencies shared across artifacts. It also defines the `.avsc` file used for Kafka's schema registry. Classes defined in this file are automatically generated using the `org.apache.avro:avro-maven-plugin:version.x` plugin. The generated classes are located in the kafka.model package of `ms-common-core`.

* **mic-data-producer (port :8080)**

This microservice/artifact is responsible for handling data input via REST protocol and producing events/messages to the Kafka broker.

To define this microservice, a hexagonal architecture has been used, divided into different modules, which can be seen in the `pom.xml` file located in the main directory of `mic-data-producer:`

    - data-boot: The boot module contains the `main` class, database configurations, exceptions, and Kafka configuration to define the default partition number for the `hotel_availability_search` topic. The `application.yml` files that consider the Spring context for startup are located in the `resources` directory of this module.

    - data-api-rest: This module defines REST controllers for data input to our application. The REST API has been defined using an API-first approach, where the `apifirst.yml` file located in the `resources` directory of this module, along with the `org.openapitools:openapi-generator-maven-plugin` plugin defined in `pom.xml`, generates the API interface for (`POST: /search` and `GET: /count`) and its DTO classes used for requests and responses. All validations will be defined in the `apifirst.yml` file. Additionally, to map immutable DTO objects to other objects like `Entity`, MapStruct has been used instead of libraries like Jackson or Gson.

    - data-application: This module contains the service logic and sending messages as a producer to the Kafka topic.

    - data-test: This module defines the application tests (unit tests have been performed).

* **mic-data-consumer (port :8089)**

The next microservice/artifact is responsible for consuming messages sent to the Kafka topic `hotel_availability_search` and persisting them in the PostgreSQL database.

This microservice uses the basic architecture used when generating a project with [SPRING INITIALZR](https://start.spring.io/).

## Run application in local

Start Docker containers using the `docker-compose` file located in the main directory (defined with version 3.9). If there is a problem with PostgreSQL, you can comment out line 15 of the file, as the project has `spring.jpa.hibernate.ddl-auto` set to `update`, which will create the database tables regardless.

Ensure that all containers are in the `Up` and `healthy` state using `docker-compose ps`.

```bash
  docker-compose up -d
```

To install the common dependency, you need to navigate to the ms-common-core directory and execute the following Maven command:

```bash
  mvn clean install
```

Go to the directory ***mic-data-producer***.

```bash
  mvn clean install
  mvn -pl .\data-boot\ spring-boot:run
```

Go to the directory ***mic-data-consumer***.

```bash
  mvn clean install
  java -jar ./target/mic-data-consumer-0.0.1-SNAPSHOT.jar
```

Once the application starts without errors, we can enter the URL of [Swagger docs](http://localhost:8080/swagger-ui/index.html) to test our application.


## Test Execution

If we want to run the tests for each application, since they are all marked with the `@Tag("UnitTest")` annotation, we only need to execute the command ```mvn test -Dgroups="UnitTest"``` in the corresponding directory.

Navigate to the ***mic-data-producer/data-test*** directory.

```bash
  mvn test -Dgroups="UnitTest"
```

Go to the directory ***mic-data-consumer***

```bash
  mvn test -Dgroups="UnitTest"
```
