package com.github.mangila.springreactiver2dbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static java.lang.String.format;

@Testcontainers
@Slf4j
public abstract class MySQLR2dbcFlywayTestContainer {

    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.28"))
            .withDatabaseName("test")
            .withUsername("mangila")
            .withPassword("password")
            .withLogConsumer(new Slf4jLogConsumer(log))
            .withReuse(Boolean.TRUE);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () ->
                format("r2dbc:pool:mysql://%s:%d/%s",
                        MY_SQL_CONTAINER.getHost(),
                        MY_SQL_CONTAINER.getFirstMappedPort(),
                        MY_SQL_CONTAINER.getDatabaseName()));
        registry.add("spring.flyway.url", () ->
                format("jdbc:mysql://%s:%d/%s",
                        MY_SQL_CONTAINER.getHost(),
                        MY_SQL_CONTAINER.getFirstMappedPort(),
                        MY_SQL_CONTAINER.getDatabaseName()));
    }

    @BeforeAll
    static void start() {
        MY_SQL_CONTAINER.start();
    }
}
