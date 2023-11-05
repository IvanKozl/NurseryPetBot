package com.example.nurserypetbot.services.interfaces;

import com.example.nurserypetbot.models.UsersContactInformation;
import com.pengrad.telegrambot.model.Message;

import java.util.List;

public interface UsersContactInformationService {
    void addNewUsersInformation(Message message);

    void sendResponse(long chatId, String string);

    UsersContactInformation read(long user_id);

    UsersContactInformation update(UsersContactInformation usersContactInformation);

    List<UsersContactInformation> getAllUsersWithActualTrialPeriod();

}
