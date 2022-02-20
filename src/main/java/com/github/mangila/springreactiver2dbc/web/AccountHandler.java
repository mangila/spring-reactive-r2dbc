package com.github.mangila.springreactiver2dbc.web;

import com.github.mangila.springreactiver2dbc.service.AccountService;
import com.github.mangila.springreactiver2dbc.web.dto.WithdrawalRequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
@AllArgsConstructor
@Slf4j
public class AccountHandler {

    private final AccountService service;

    @NonNull
    public Mono<ServerResponse> findByAccountNumber(ServerRequest serverRequest) {
        return service.findByAccountNumber(serverRequest.pathVariable("accountNumber"))
                .flatMap(clientResponse -> ServerResponse.ok().bodyValue(clientResponse))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @NonNull
    public Mono<ServerResponse> withdraw(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(WithdrawalRequestDTO.class)
                .flatMap(request -> service.withdraw(request.getAccountNumber(), request.getAmount(), request.getCurrencyCode())
                        .flatMap(response -> ServerResponse.accepted().bodyValue(response)))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(t -> {
                    log.error("error", t);
                    return ServerResponse.badRequest().bodyValue("Not enough credits");
                });
    }

}
