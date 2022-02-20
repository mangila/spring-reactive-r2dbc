# spring-reactive-r2dbc

ATM machine RESTful API. <br>
Sample app with Spring Webflux, JavaMoney library, Flyway and tests with Testcontainers.

## OpenAPI
* http://localhost:8080/swagger-ui.html
* Account - http://localhost:8080/webjars/swagger-ui/index.html?urls.primaryName=Account
* Currency - http://localhost:8080/webjars/swagger-ui/index.html?urls.primaryName=Currency

## Docker

* https://hub.docker.com/r/mangila/spring-reactive-r2dbc
* ``docker-compose up -d`` installs a MySQL image and starts the app from the DockerHub instance
* ``docker-compose -f docker-compose.db.yml up -d`` to get a local MySQL image and starts the app inside your preferred
  IDEA.

## Testcontainers
* ``testcontainers.properties`` file must be modified with the flag ``testcontainers.reuse.enable=true`` on your local machine.


