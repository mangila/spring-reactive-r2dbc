package com.github.mangila.springreactiver2dbc.common;

import org.javamoney.moneta.FastMoney;
import org.springdoc.core.converters.models.MonetaryAmount;

import javax.money.Monetary;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;

public final class MoneyUtils {

    private static final ExchangeRateProvider EXCHANGE_RATE_PROVIDER = MonetaryConversions.getExchangeRateProvider();

    private MoneyUtils() {
        throw new ApplicationException("Don't invoke me");
    }

    /**
     * Compute withdrawal from account balance.
     *
     * @return account balance with local currency
     */
    public static FastMoney computeWithdrawal(FastMoney withdrawal, FastMoney convertedBalance, String accountCurrencyCode) {
        double newBalance = convertedBalance
                .subtract(withdrawal)
                .getNumber()
                .doubleValue();
        return getFastMoney(convertedBalance.getCurrency().getCurrencyCode(), newBalance, accountCurrencyCode);
    }

    /**
     * @return FastMoney instance
     */
    public static FastMoney getFastMoney(String currencyCode, double number) {
        return Monetary.getAmountFactory(FastMoney.class)
                .setCurrency(currencyCode)
                .setNumber(number)
                .create()
                .with(Monetary.getDefaultRounding());
    }

    /**
     * @return FastMoney instance with its number converted to @param conversionCurrencyCode
     */
    public static FastMoney getFastMoney(String currencyCode, double number, String conversionCurrencyCode) {
        return getFastMoney(currencyCode, number)
                .with(getCurrencyConversion(conversionCurrencyCode));
    }

    private static CurrencyConversion getCurrencyConversion(String currencyCode) {
        return EXCHANGE_RATE_PROVIDER.getCurrencyConversion(currencyCode);
    }

}
