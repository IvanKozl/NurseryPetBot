-- liquibase formatted sql

-- changeset Maria:1
create table notification_task (
id bigint primary key,
chat_id bigint unique,
date_time timestamp without time zone not null,
message varchar(100) not null
);
