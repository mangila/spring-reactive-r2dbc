package com.github.mangila.springreactiver2dbc.persistence.transaction;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ClientTransactionRepository extends ReactiveCrudRepository<ClientTransaction, String> {
    Flux<ClientTransaction> findAllByAccountNumber(String accountNumber);
}
