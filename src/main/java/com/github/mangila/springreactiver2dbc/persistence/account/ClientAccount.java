package com.github.mangila.springreactiver2dbc.persistence.account;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import reactor.util.annotation.NonNull;

@Data
public class ClientAccount implements Persistable<String> {
    @Id
    @NonNull
    private String accountNumber;
    private double balance;
    private String currencyCode;
    @Transient
    private boolean isNew;

    @Transient
    @Override
    public String getId() {
        return accountNumber;
    }

    @Transient
    @Override
    public boolean isNew() {
        return isNew;
    }
}
