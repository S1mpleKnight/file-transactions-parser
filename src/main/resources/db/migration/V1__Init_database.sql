create table role
(
    id      int(11) not null auto_increment
        primary key,
    name    varchar(5)  not null
);

create table operator
(
    id         int(11) not null auto_increment
        primary key,
    first_name varchar(20) not null,
    last_name  varchar(30) not null,
    nickname   varchar(15) not null,
    password   varchar(50) not null,
    role_id    int(11) not null,
    constraint operator_role_id_fk
        foreign key (role_id) references role (id)
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
    operator_id         bigint  not null
);

