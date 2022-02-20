package com.github.mangila.springreactiver2dbc.web.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ClientTransactionDTO {
    private String amount;
    private Instant created;
    private String uuid;
}
