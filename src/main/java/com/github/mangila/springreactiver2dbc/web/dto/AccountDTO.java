package com.github.mangila.springreactiver2dbc.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountDTO {
    private String accountNumber;
    private Double balance;
    private String currency;
    private List<ClientTransactionDTO> clientTransactions;
}
