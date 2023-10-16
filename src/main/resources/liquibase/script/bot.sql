-- liquibase formatted sql

-- changeset1 NurseryCatDogBot
CREATE TABLE notification_task
(
    chat_id      SERIAL PRIMARY KEY,
    message      TEXT NOT NULL,
    message_time TIMESTAMP,
);
CREATE INDEX userID ON notification_task (chat_id);