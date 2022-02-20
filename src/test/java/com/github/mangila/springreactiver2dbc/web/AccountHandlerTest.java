package com.github.mangila.springreactiver2dbc.web;

import com.github.mangila.springreactiver2dbc.MySQLR2dbcFlywayTestContainer;
import com.github.mangila.springreactiver2dbc.persistence.account.ClientAccount;
import com.github.mangila.springreactiver2dbc.persistence.account.ClientAccountRepository;
import com.github.mangila.springreactiver2dbc.persistence.transaction.ClientTransaction;
import com.github.mangila.springreactiver2dbc.persistence.transaction.ClientTransactionRepository;
import com.github.mangila.springreactiver2dbc.web.dto.AccountDTO;
import com.github.mangila.springreactiver2dbc.web.dto.WithdrawalRequestDTO;
import com.github.mangila.springreactiver2dbc.web.dto.WithdrawalResponseDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@SpringBootTest
@AutoConfigureWebTestClient
class AccountHandlerTest extends MySQLR2dbcFlywayTestContainer {

    @Autowired
    private WebTestClient http;
    @Autowired
    private ClientAccountRepository accountRepository;
    @Autowired
    private ClientTransactionRepository transactionRepository;

    @BeforeEach
    void populate() {
        var c = new ClientAccount();
        c.setAccountNumber("1337");
        c.setCurrencyCode("SEK");
        c.setBalance(1000d);
        c.setNew(Boolean.TRUE);
        var t = ClientTransaction.builder()
                .accountNumber("1337")
                .amount("SEK 10.00")
                .uuid(UUID.randomUUID().toString())
                .created(Instant.now())
                .build();
        Mono.when(accountRepository.save(c), transactionRepository.save(t)).block();
    }

    @AfterEach
    void truncate() {
        Mono.when(accountRepository.deleteAll(), transactionRepository.deleteAll()).block();
    }

    @Test
    void shouldFindByAccountNumber() {
        http.get()
                .uri("/api/v1/account/1337")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(AccountDTO.class)
                .value(AccountDTO::getAccountNumber, Matchers.equalTo("1337"))
                .value(AccountDTO::getBalance, Matchers.equalTo(1000d))
                .value(AccountDTO::getCurrency, Matchers.equalTo("SEK"))
                .value(AccountDTO::getClientTransactions, Matchers.hasSize(1));
    }

    @Test
    void shouldNotFindByAccountNumber() {
        http.get()
                .uri("/api/v1/account/account-not-exist")
                .exchange()
                .expectStatus()
                .is4xxClientError();
    }

    @Test
    void withdraw() {
        var request = new WithdrawalRequestDTO("1337", 10, "SEK");
        http.put()
                .uri("/api/v1/account")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(WithdrawalResponseDTO.class)
                .value(WithdrawalResponseDTO::getAccountNumber, Matchers.equalTo("1337"))
                .value(WithdrawalResponseDTO::getAmount, Matchers.equalTo("SEK 10.00"))
                .value(WithdrawalResponseDTO::getBalance, Matchers.equalTo(990d));

        http.get()
                .uri("/api/v1/account/1337")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(AccountDTO.class)
                .value(AccountDTO::getAccountNumber, Matchers.equalTo("1337"))
                .value(AccountDTO::getBalance, Matchers.equalTo(990d))
                .value(AccountDTO::getCurrency, Matchers.equalTo("SEK"))
                .value(AccountDTO::getClientTransactions, Matchers.hasSize(2));
    }
}