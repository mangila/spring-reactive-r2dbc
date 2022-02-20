package com.github.mangila.springreactiver2dbc.service;

import com.github.mangila.springreactiver2dbc.common.AccountBalanceUtils;
import com.github.mangila.springreactiver2dbc.common.ApplicationException;
import com.github.mangila.springreactiver2dbc.common.MoneyUtils;
import com.github.mangila.springreactiver2dbc.persistence.account.ClientAccountRepository;
import com.github.mangila.springreactiver2dbc.persistence.transaction.ClientTransaction;
import com.github.mangila.springreactiver2dbc.persistence.transaction.ClientTransactionRepository;
import com.github.mangila.springreactiver2dbc.web.dto.AccountDTO;
import com.github.mangila.springreactiver2dbc.web.dto.WithdrawalResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.FastMoney;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class AccountService {

    private final ClientAccountRepository clientAccountRepository;
    private final ClientTransactionRepository clientTransactionRepository;
    private final AccountMapper accountMapper;

    public Mono<AccountDTO> findByAccountNumber(String accountNumber) {
        return Mono.zip(clientAccountRepository.findById(accountNumber),
                clientTransactionRepository.findAllByAccountNumber(accountNumber).collectList(),
                accountMapper::toDTO);
    }

    @Transactional
    public Mono<WithdrawalResponseDTO> withdraw(String accountNumber, Integer amount, String currencyCode) {
        return clientAccountRepository.findById(accountNumber)
                .flatMap(account -> {
                    FastMoney withdrawal = MoneyUtils.getFastMoney(currencyCode, amount);
                    FastMoney convertedBalance = MoneyUtils.getFastMoney(account.getCurrencyCode(), account.getBalance(), currencyCode);
                    if (AccountBalanceUtils.hasCredit(convertedBalance, withdrawal)) {
                        FastMoney newBalance = MoneyUtils.computeWithdrawal(withdrawal, convertedBalance, account.getCurrencyCode());
                        String uuid = UUID.randomUUID().toString();
                        Instant instant = Instant.now();
                        var updateBalance = clientAccountRepository.updateBalance(accountNumber, newBalance.getNumber().doubleValue());
                        var saveTransaction = clientTransactionRepository.save(ClientTransaction.builder()
                                .uuid(uuid)
                                .amount(withdrawal.toString())
                                .accountNumber(accountNumber)
                                .created(instant)
                                .build());
                        return Mono.when(updateBalance, saveTransaction)
                                .doOnSuccess(voidMethod -> log.info(String.format("%s withdrew: %s", accountNumber, withdrawal)))
                                .then(Mono.just(WithdrawalResponseDTO.builder()
                                        .uuid(uuid)
                                        .accountNumber(accountNumber)
                                        .amount(withdrawal.toString())
                                        .created(instant)
                                        .balance(newBalance.getNumber().doubleValue())
                                        .build()));
                    } else {
                        return Mono.error(ApplicationException::new);
                    }
                });
    }
}
