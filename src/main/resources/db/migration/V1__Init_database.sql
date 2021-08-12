create table operator
(
    id         bigint not null
        primary key,
    first_name varchar(20) not null,
    last_name  varchar(30) not null,
    nickname   varchar(15) not null,
    password   varchar(50) not null
);

create table role
(
    id      varchar(36) not null
        primary key,
    role    varchar(5)  not null
);

create table transaction
(
    id                  varchar(36)  not null
        primary key,
    status              tinyint(1)   not null,
    currency            varchar(3)   not null,
    amount              bigint       not null,
    customer_id         varchar(36)  not null,
    customer_first_name varchar(20)  null,
    customer_last_name  varchar(30)  null,
    customer_email      varchar(256) null,
    date_time           timestamp    not null,
    operator_id             varchar(36)  not null,
    constraint transaction_operator_id_fk
        foreign key (operator_id) references operator (id)
);

