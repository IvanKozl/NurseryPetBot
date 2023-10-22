-- liquibase formatted sql

-- changeset Maria:2
CREATE TABLE users_contact
(
id serial PRIMARY KEY,
name varchar NOT NULL,
surname varchar NOT NULL,
age int NOT NULL,
phone serial UNIQUE NOT NULL,
email text UNIQUE NOT NULL,
users_chat_id integer REFERENCES notification_task(chat_id)
);





