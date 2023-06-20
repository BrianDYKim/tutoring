create table students
(
    id         bigint(20) auto_increment primary key,
    email      varchar(40)  not null,
    password   varchar(150) not null,
    nickname   varchar(40)  not null,
    created_at datetime     not null default now(),
    updated_at datetime     null,
    deleted_at datetime     null,
    is_deleted tinyint(1)   not null default 0
);