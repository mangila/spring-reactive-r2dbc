package com.github.mangila.springreactiver2dbc.persistence.transaction;

import com.github.mangila.springreactiver2dbc.MySQLR2dbcFlywayTestContainer;
import com.github.mangila.springreactiver2dbc.config.FlywayConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.UUID;

@DataR2dbcTest
@Import(FlywayConfig.class)
class ClientTransactionRepositoryTest extends MySQLR2dbcFlywayTestContainer {

    @Autowired
    private ClientTransactionRepository repository;

    @BeforeEach
    void populate() {
        var t = ClientTransaction.builder()
                .accountNumber("1337")
                .amount("SEK 10.00")
                .uuid(UUID.randomUUID().toString())
                .created(Instant.now())
                .build();

        var t1 = ClientTransaction.builder()
                .accountNumber("1337")
                .amount("SEK 110.00")
                .uuid(UUID.randomUUID().toString())
                .created(Instant.now())
                .build();
        repository.saveAll(Flux.just(t, t1)).blockLast();
    }

    @AfterEach
    void truncate() {
        repository.deleteAll().block();
    }

    @Test
    void findAllByAccountNumber() {
        var listMono = repository.findAllByAccountNumber("1337").collectList();
        StepVerifier.create(listMono)
                .expectNextMatches(l -> l.size() == 2)
                .verifyComplete();
    }
}