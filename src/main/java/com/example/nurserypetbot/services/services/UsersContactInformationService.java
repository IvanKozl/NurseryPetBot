package com.example.nurserypetbot.services.services;

import com.example.nurserypetbot.models.UsersContactInformation;
import com.pengrad.telegrambot.model.Message;

import java.util.List;

public interface UsersContactInformationService {
    void addNewUsersInformation(Message message);
    UsersContactInformation read(long user_id);
    UsersContactInformation update(UsersContactInformation usersContactInformation);

    List<UsersContactInformation> getAllUsersWithActualTrailPeriod();


}
