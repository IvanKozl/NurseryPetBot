-- liquibase formatted sql

-- changeset Maria:1
create table notification_task (
chat_id serial primary key,
date_time timestamp without time zone not null,
message varchar(100) not null);
