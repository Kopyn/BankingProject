# BankingProject
This project aims to simulate the behaviour of a banking system. The architecture is based on CQRS and Event Sourcing patterns and the project is entirely focused on learning the basics of distributed and event-sourced systems, therefore I tried mixing different approaches to try and learn upsides as well as downsides of each approach.
Command side of the system is handled by account-service, customer-service and transaction-service which serves as a SAGA orchestrator for inter-account money transfers.
Query side is handled by customer-service but also one query-specific service - account-info-service. Customer-service should ideally be broken into separate services, so queries and commands could be handled and scaled separately.
I also included a spring-cloud-gateway as an entry point to the system. It is used as oauth2 client, it stores user sessions with redis and is a good approach for any request and/or response filtering in the future.

## Breakdown of project components:
* Gateway application - entry point into the system, this is a standard spring-cloud-gateway with redis storage and serves as oauth2 client.
* Account-service
* Customer-service
* Transaction-service
* Account-info-service

## Tech stack
* Java with SpringWebflux for asynchronous and non-blocking command processing
* Apache Kafka - used for cross-service communication, especially useful for money transfer SAGA, handling credit or debit failures etc.
* MongoDB - eventstore and with the usage of Mongo Change Streams it provides an event bus to handle query-side projection (this approach is not ideal because and is not a perfect Transactional Outbox Pattern)
* Keycloak - Local instance of keycloak as my authorization server
