package com.github.mangila.springreactiver2dbc.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.money.CurrencyUnit;

@Data
@NoArgsConstructor
public class CurrencyDTO {
    private String currencyCode;

    public CurrencyDTO(CurrencyUnit currencyUnit) {
        this.currencyCode = currencyUnit.getCurrencyCode();
    }
}
