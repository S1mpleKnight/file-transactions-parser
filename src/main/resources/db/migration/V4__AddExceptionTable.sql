CREATE TABLE error_msg
(
    id              bigint primary key auto_increment,
    error_msg       varchar(255),
    operator_id     int,
    error_time      timestamp,
    constraint error_msg_operator_fk
        foreign key (operator_id) references operator (id)
            on update cascade on delete set null
);