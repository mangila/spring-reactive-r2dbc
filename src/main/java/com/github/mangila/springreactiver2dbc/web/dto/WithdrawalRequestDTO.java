package com.github.mangila.springreactiver2dbc.web.dto;

import lombok.Data;

@Data
public class WithdrawalRequestDTO {
    private final String accountNumber;
    private final Integer amount;
    private final String currencyCode;
}
