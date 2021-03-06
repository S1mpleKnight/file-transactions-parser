create table role
(
    id      int not null auto_increment
        primary key,
    name    varchar(5)  not null
);

create table operator
(
    id         int not null auto_increment
        primary key,
    first_name varchar(20) not null,
    last_name  varchar(30) not null,
    nickname   varchar(15) not null unique,
    password   varchar(255) not null,
    role_id    int not null,
    constraint operator_role_id_fk
        foreign key (role_id) references role (id)
            on update cascade on delete cascade
);

create table transactions
(
    id                  varchar(36)  not null
        primary key,
    status              tinyint(1)   not null,
    currency            varchar(3)   not null,
    amount              bigint       not null,
    customer_id         varchar(36)  not null,
    date_time           timestamp    not null,
    operator_id         int     null,
    constraint transactions_operator_id_fk
        foreign key (operator_id) references operator (id)
           on update cascade on delete set null
);