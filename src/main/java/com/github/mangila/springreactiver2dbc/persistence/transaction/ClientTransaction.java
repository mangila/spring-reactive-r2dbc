package com.github.mangila.springreactiver2dbc.persistence.transaction;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import reactor.util.annotation.NonNull;

import java.time.Instant;

@Data
@Builder
public class ClientTransaction implements Persistable<String> {
    @Id
    @NonNull
    private String uuid;
    private String accountNumber;
    private String amount;
    private Instant created;

    @Transient
    @Override
    public String getId() {
        return uuid;
    }

    @Transient
    @Override
    public boolean isNew() {
        return true;
    }
}
