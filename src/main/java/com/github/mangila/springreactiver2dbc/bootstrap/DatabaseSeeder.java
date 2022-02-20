package com.github.mangila.springreactiver2dbc.bootstrap;

import com.github.mangila.springreactiver2dbc.persistence.account.ClientAccount;
import com.github.mangila.springreactiver2dbc.persistence.account.ClientAccountRepository;
import com.github.mangila.springreactiver2dbc.persistence.transaction.ClientTransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Profile("dev")
@AllArgsConstructor
@Slf4j
public class DatabaseSeeder implements CommandLineRunner, DisposableBean {

    private final ClientAccountRepository clientAccountRepository;
    private final ClientTransactionRepository clientTransactionRepository;

    @Override
    public void run(String... args) {
        var a = new ClientAccount();
        a.setCurrencyCode("SEK");
        a.setAccountNumber("A1");
        a.setBalance(200d);
        a.setNew(Boolean.TRUE);
        var b = new ClientAccount();
        b.setCurrencyCode("SEK");
        b.setAccountNumber("A2");
        b.setBalance(110d);
        b.setNew(Boolean.TRUE);
        var c = new ClientAccount();
        c.setCurrencyCode("USD");
        c.setAccountNumber("A3");
        c.setBalance(90d);
        c.setNew(Boolean.TRUE);
        clientAccountRepository.saveAll(Flux.just(a, b, c)).subscribe();
    }

    @Override
    public void destroy() {
        Mono.when(clientAccountRepository.deleteAll(), clientTransactionRepository.deleteAll()).block();
    }
}
