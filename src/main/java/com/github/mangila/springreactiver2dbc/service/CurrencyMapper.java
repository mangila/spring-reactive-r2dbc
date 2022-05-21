package com.github.mangila.springreactiver2dbc.service;

import com.github.mangila.springreactiver2dbc.web.dto.CurrencyDTO;
import org.springframework.stereotype.Component;

import javax.money.CurrencyUnit;
import java.util.Collection;
import java.util.List;

@Component
public class CurrencyMapper {

    public List<CurrencyDTO> toDTO(Collection<CurrencyUnit> currencyUnits) {
        return currencyUnits.stream().map(CurrencyDTO::new).toList();
    }
}
