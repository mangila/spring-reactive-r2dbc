package com.github.mangila.springreactiver2dbc.common;

import org.junit.jupiter.api.Test;

import javax.money.MonetaryException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountBalanceUtilsTest {

    @Test
    void hasCredit() {
        var balance = MoneyUtils.getFastMoney("SEK", 10d);
        var withdrawal = MoneyUtils.getFastMoney("SEK", 10d);
        assertThat(AccountBalanceUtils.hasCredit(balance, withdrawal)).isTrue();
    }

    @Test
    void hasCreditReturnFalse() {
        var balance = MoneyUtils.getFastMoney("SEK", 10d);
        var withdrawal = MoneyUtils.getFastMoney("SEK", 10.5d);
        assertThat(AccountBalanceUtils.hasCredit(balance, withdrawal)).isFalse();
    }

    @Test
    void hasCreditCurrencyMismatch() {
        var balance = MoneyUtils.getFastMoney("SEK", 10d);
        var withdrawal = MoneyUtils.getFastMoney("USD", 10d);
        assertThatThrownBy(() -> AccountBalanceUtils.hasCredit(balance, withdrawal)).isInstanceOf(MonetaryException.class);
    }
}