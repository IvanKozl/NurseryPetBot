-- liquibase formatted sql

-- changeset Maria:1
create table notification_task (
id bigint generated by default as identity not null,
chat_id bigint not null,
date_time timestamp without time zone not null,
message varchar(100) not null,
CONSTRAINT "notifyId" PRIMARY KEY (id));
