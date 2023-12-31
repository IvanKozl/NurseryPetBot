-- liquibase formatted sql

-- changeset Maria:4
CREATE TABLE nurserycatdogbot.dog
(
id bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
chat_id bigint,
type varchar NOT NULL,
name varchar NOT NULL,
gender varchar NOT NULL,
age int NOT NULL,
sterile varchar NOT NULL,
feature varchar,
feature_add text,
user_id bigint REFERENCES nurserycatdogbot.users_contact_information (id),
PRIMARY KEY (id));