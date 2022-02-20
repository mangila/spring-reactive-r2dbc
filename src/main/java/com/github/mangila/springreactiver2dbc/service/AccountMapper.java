package com.github.mangila.springreactiver2dbc.service;

import com.github.mangila.springreactiver2dbc.persistence.account.ClientAccount;
import com.github.mangila.springreactiver2dbc.persistence.transaction.ClientTransaction;
import com.github.mangila.springreactiver2dbc.web.dto.AccountDTO;
import com.github.mangila.springreactiver2dbc.web.dto.ClientTransactionDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {

    public AccountDTO toDTO(ClientAccount clientAccount, List<ClientTransaction> clientTransactions) {
        return AccountDTO.builder()
                .accountNumber(clientAccount.getAccountNumber())
                .balance(clientAccount.getBalance())
                .currency(clientAccount.getCurrencyCode())
                .clientTransactions(clientTransactions.stream().map(clientTransaction -> ClientTransactionDTO.builder()
                        .amount(clientTransaction.getAmount())
                        .created(clientTransaction.getCreated())
                        .uuid(clientTransaction.getUuid())
                        .build()).toList())
                .build();
    }
}
