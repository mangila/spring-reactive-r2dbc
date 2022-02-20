package com.github.mangila.springreactiver2dbc.config;

import lombok.Setter;
import org.flywaydb.core.Flyway;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "spring")
public class FlywayConfig {

    @Setter
    private Properties flyway;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return new Flyway(Flyway.configure()
                .baselineOnMigrate(Boolean.parseBoolean(flyway.getProperty("baseline-on-migrate")))
                .table(flyway.getProperty("table"))
                .dataSource(flyway.getProperty("url"), flyway.getProperty("user"), flyway.getProperty("password"))
                .locations(flyway.getProperty("locations")));
    }

}
