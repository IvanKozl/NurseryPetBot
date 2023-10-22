package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.models.Notification;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.parser.Parser;
import com.example.nurserypetbot.repository.UsersContactInformationRepository;
import com.example.nurserypetbot.services.services.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;


@Service
public class UsersContactInformationImpl implements UsersContactInformationService {

    private final TelegramBot telegramBot;

    private final UsersContactInformationRepository repository;

    public UsersContactInformationImpl(TelegramBot telegramBot, UsersContactInformationRepository repository) {
        this.telegramBot = telegramBot;
        this.repository = repository;
    }

    @Override
    public void addNewUsersInformation(Message message) {
        Notification notification = new Notification();
        UsersContactInformation usersContactInformation;
        long chatId = message.chat().id();
        SendMessage result;

        try {

            usersContactInformation = Parser.tryToParseUsersInformation(notification.getMessage());
            notification.setChatId(chatId);

        } catch (Exception ex) {
            telegramBot.execute(new SendMessage(chatId, "Wrong format"));
            return;
        }

        repository.save(usersContactInformation);
        result = new SendMessage(chatId, String.format("OK"));
        telegramBot.execute(result);

    }
}

