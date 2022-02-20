package com.github.mangila.springreactiver2dbc.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CurrencyDTO {
    private String currencyCode;

    public CurrencyDTO(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
