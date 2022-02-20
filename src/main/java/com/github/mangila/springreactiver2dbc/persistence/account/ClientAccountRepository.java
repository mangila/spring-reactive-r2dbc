package com.github.mangila.springreactiver2dbc.persistence.account;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ClientAccountRepository extends ReactiveCrudRepository<ClientAccount, String> {
    @Query("UPDATE client_account SET balance = :newBalance WHERE account_number = :accountNumber")
    Mono<Void> updateBalance(String accountNumber, Double newBalance);
}
