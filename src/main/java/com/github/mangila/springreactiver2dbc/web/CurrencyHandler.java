package com.github.mangila.springreactiver2dbc.web;

import com.github.mangila.springreactiver2dbc.service.CurrencyService;
import com.github.mangila.springreactiver2dbc.web.dto.CurrencyDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

@Component
@AllArgsConstructor
@Slf4j
public class CurrencyHandler {

    private final CurrencyService service;

    @NonNull
    public Mono<ServerResponse> getCurrencies() {
        return ServerResponse.ok().body(service.getCurrencies(), CurrencyDTO.class);
    }

}
