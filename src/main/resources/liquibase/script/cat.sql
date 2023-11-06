-- liquibase formatted sql

-- changeset Maria:3
CREATE TABLE nurserycatdogbot1.cat
(
id bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
chat_id bigint,
type varchar NOT NULL,
name varchar NOT NULL,
gender varchar NOT NULL,
age int NOT NULL,
sterile varchar NOT NULL,
feature varchar ,
feature_add text,
user_id bigint REFERENCES nurserycatdogbot1.users_contact_information (id),
PRIMARY KEY (id)
);