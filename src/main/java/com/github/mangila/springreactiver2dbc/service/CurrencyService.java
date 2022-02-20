package com.github.mangila.springreactiver2dbc.service;

import com.github.mangila.springreactiver2dbc.web.dto.CurrencyDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.money.Monetary;

@Service
@AllArgsConstructor
@Slf4j
public class CurrencyService {

    private final CurrencyMapper currencyMapper;

    public Flux<CurrencyDTO> getCurrencies() {
        return Flux.fromIterable(currencyMapper.toDTO(Monetary.getCurrencies()));
    }
}
