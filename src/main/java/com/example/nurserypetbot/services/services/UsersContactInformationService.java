package com.example.nurserypetbot.services.services;

import com.pengrad.telegrambot.model.Message;

public interface UsersContactInformationService {
    void addNewUsersInformation(Message message);
    void addReport(Message message);

    void sendResponse(long chatId, String string);
}
