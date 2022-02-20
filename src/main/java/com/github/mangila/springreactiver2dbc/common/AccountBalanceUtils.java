package com.github.mangila.springreactiver2dbc.common;

import org.javamoney.moneta.FastMoney;

public final class AccountBalanceUtils {

    private AccountBalanceUtils() {
        throw new ApplicationException("Don't invoke me");
    }

    public static boolean hasCredit(FastMoney accountBalance, FastMoney withdrawal) {
        return !accountBalance.isNegativeOrZero() && !accountBalance.isLessThan(withdrawal);
    }
}
