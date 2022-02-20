package com.github.mangila.springreactiver2dbc.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenApiCustomiser customOpenAPI(@Value("${app.description}") String description,
                                           @Value("${app.version}") String version,
                                           @Value("${app.title}") String title) {
        return openApi -> openApi.info(new Info()
                .title(title)
                .version(version)
                .description(description)
                .contact(new Contact().name("Mangila"))
                .license(new License()
                        .name("MIT")
                        .url("https://github.com/mangila/spring-reactive-r2dbc/blob/master/LICENSE")));
    }
}
