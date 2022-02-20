package com.github.mangila.springreactiver2dbc.persistence.account;

import com.github.mangila.springreactiver2dbc.MySQLR2dbcFlywayTestContainer;
import com.github.mangila.springreactiver2dbc.config.FlywayConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import reactor.test.StepVerifier;

@DataR2dbcTest
@Import(FlywayConfig.class)
class ClientAccountRepositoryTest extends MySQLR2dbcFlywayTestContainer {

    @Autowired
    private ClientAccountRepository repository;

    @BeforeEach
    void populate() {
        var c = new ClientAccount();
        c.setAccountNumber("1337");
        c.setCurrencyCode("SEK");
        c.setBalance(1000d);
        c.setNew(Boolean.TRUE);
        repository.save(c).block();
    }

    @AfterEach
    void truncate() {
        repository.deleteAll().block();
    }

    @Test
    void updateBalance() {
        repository.updateBalance("1337", 600d).block();
        var mono = repository.findById("1337");
        StepVerifier.create(mono)
                .expectNextMatches(c -> c.getBalance() == 600d)
                .verifyComplete();
    }
}