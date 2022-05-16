package com.github.mangila.springreactiver2dbc.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MoneyUtilsTest {

    @Test
    void computeWithdrawal() {
        var balance = MoneyUtils.getFastMoney("SEK", 100d, "SEK");
        var withdrawal = MoneyUtils.getFastMoney("SEK", 1d);
        balance = MoneyUtils.computeWithdrawal(withdrawal, balance, "SEK");
        assertThat(balance.getNumber().doubleValue()).isEqualTo(99d);
    }
}