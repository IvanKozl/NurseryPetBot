-- liquibase formatted sql

-- changeset2 NurseryCatDogBot

create table users_contact(
id serial primary key,
users_contact_information text not null);

select n.chat_id, u.id
from notification_task n inner join user_contacts u on n.chat_id = u.id;


