package com.github.mangila.springreactiver2dbc.config;

import com.github.mangila.springreactiver2dbc.web.AccountHandler;
import com.github.mangila.springreactiver2dbc.web.dto.AccountDTO;
import com.github.mangila.springreactiver2dbc.web.dto.WithdrawalRequestDTO;
import com.github.mangila.springreactiver2dbc.web.dto.WithdrawalResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@Slf4j
@AllArgsConstructor
public class AccountRouterConfig {

    private final OpenApiCustomiser openAPIConfig;

    @Bean
    @RouterOperations
            ({
                    @RouterOperation(
                            path = "/api/v1/account",
                            consumes = {MediaType.APPLICATION_JSON_VALUE},
                            produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.PUT, beanClass = AccountHandler.class, beanMethod = "withdraw",
                            operation = @Operation(operationId = "withdraw",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "202",
                                                    description = "Successful operation",
                                                    content = @Content(schema = @Schema(implementation = WithdrawalResponseDTO.class))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "400",
                                                    description = "Not enough credits",
                                                    content = @Content(schema = @Schema(defaultValue = "Not enough credits"))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Account not found",
                                                    content = @Content(schema = @Schema(hidden = true))
                                            )
                                    },
                                    requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = WithdrawalRequestDTO.class)))
                            )
                    ),
                    @RouterOperation(
                            path = "/api/v1/account/{accountNumber}",
                            produces = {MediaType.APPLICATION_JSON_VALUE},
                            method = RequestMethod.GET, beanClass = AccountHandler.class, beanMethod = "findByAccountNumber",
                            operation = @Operation(operationId = "findByAccountNumber",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Successful operation",
                                                    content = @Content(schema = @Schema(implementation = AccountDTO.class))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Account not found",
                                                    content = @Content(schema = @Schema(hidden = true))
                                            )
                                    },
                                    parameters = {@Parameter(in = ParameterIn.PATH, name = "accountNumber")}
                            )
                    )
            })
    public RouterFunction<ServerResponse> accountRoutes(AccountHandler handler) {
        return route()
                .path("api/v1/account", builder -> builder
                        .PUT(accept(MediaType.APPLICATION_JSON), handler::withdraw)
                        .GET("/{accountNumber}", accept(MediaType.APPLICATION_JSON), handler::findByAccountNumber)
                        .build())
                .build();
    }

    @Bean
    public GroupedOpenApi accountApi() {
        return GroupedOpenApi.builder()
                .group("Account")
                .pathsToMatch("/api/v1/account/**")
                .addOpenApiCustomiser(openAPIConfig)
                .build();
    }
}
