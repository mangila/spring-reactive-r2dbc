CREATE TABLE client_account
(
    account_number varchar(10) not null,
    balance        decimal(6, 2),
    currency_code  varchar(3),
    primary key (account_number)
);
CREATE TABLE client_transaction
(
    uuid           varchar(36) not null,
    account_number varchar(10),
    amount         varchar(100),
    created        datetime,
    primary key (uuid)
);