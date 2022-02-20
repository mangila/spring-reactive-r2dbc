package com.github.mangila.springreactiver2dbc.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class WithdrawalResponseDTO {
    private String uuid;
    private String accountNumber;
    private String amount;
    private Instant created;
    private Double balance;
}
