-- liquibase formatted sql

-- changeset Maria:2
CREATE TABLE users_contact_information
(
id serial PRIMARY KEY,
chat_id bigint NOT NULL,
name varchar NOT NULL,
surname varchar NOT NULL,
age int NOT NULL,
phone serial UNIQUE NOT NULL,
email text UNIQUE NOT NULL
);





