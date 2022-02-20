package com.github.mangila.springreactiver2dbc.web;

import com.github.mangila.springreactiver2dbc.MySQLR2dbcFlywayTestContainer;
import com.github.mangila.springreactiver2dbc.web.dto.CurrencyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class CurrencyHandlerTest extends MySQLR2dbcFlywayTestContainer {

    @Autowired
    private WebTestClient http;

    @Test
    void getCurrencies() {
        http.get()
                .uri("/api/v1/currency")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(CurrencyDTO.class);
    }
}