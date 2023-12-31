-- liquibase formatted sql

-- changeset Maria:2
CREATE TABLE nurserycatdogbot.users_contact_information
(
id bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
chat_id bigint NOT NULL,
name varchar NOT NULL,
surname varchar NOT NULL,
trial_period DATE,
age int NOT NULL,
phone bigint UNIQUE NOT NULL,
email text UNIQUE NOT NULL,
pet_shelter_type varchar NOT NULL,
PRIMARY KEY (id));