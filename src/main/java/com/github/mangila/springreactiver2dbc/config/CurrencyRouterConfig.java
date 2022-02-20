package com.github.mangila.springreactiver2dbc.config;

import com.github.mangila.springreactiver2dbc.web.CurrencyHandler;
import com.github.mangila.springreactiver2dbc.web.dto.CurrencyDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Slf4j
@AllArgsConstructor
public class CurrencyRouterConfig {

    private final OpenApiCustomiser openAPIConfig;

    @Bean
    @RouterOperations
            ({
                    @RouterOperation(
                            path = "/api/v1/currency",
                            produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.GET, beanClass = CurrencyHandler.class, beanMethod = "getCurrencies",
                            operation = @Operation(operationId = "getCurrencies",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Successful operation",
                                                    content = @Content(schema = @Schema(implementation = CurrencyDTO.class))
                                            ),
                                    })
                    )
            })
    public RouterFunction<ServerResponse> currencyRoutes(CurrencyHandler handler) {
        return route().GET("api/v1/currency", serverRequest -> handler.getCurrencies()).build();
    }

    @Bean
    public GroupedOpenApi moneyApi() {
        return GroupedOpenApi.builder()
                .group("Currency")
                .pathsToMatch("/api/v1/currency/**")
                .addOpenApiCustomiser(openAPIConfig)
                .build();
    }
}
