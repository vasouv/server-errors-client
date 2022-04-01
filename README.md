# server-errors-client
Web application showcasing how different HTTP clients make a request and behave according to the HTTP status they receive.

This is a client project to the `server-errors` project found [here](https://github.com/vasouv/server-errors).

## Technologies
- Java 17
- Spring Boot 2.6.4

## Build and Run
Maven: `mvn spring-boot:run`

Java: `java -jar target\server-errors-client.jar`

## Endpoints
The root endpoints for the two clients used are `template` for RestTemplate and `apache` for Apache Client.

The URL of the target project can be configured in the `application.properties` config file.

### RestTemplate
`localhost:8081/template/none`

`localhost:8081/template/client`

`localhost:8081/template/server`

`localhost:8081/template/random`

### Apache Client
`localhost:8081/apache/none`

`localhost:8081/apache/client`

`localhost:8081/apache/server`

`localhost:8081/apache/random`