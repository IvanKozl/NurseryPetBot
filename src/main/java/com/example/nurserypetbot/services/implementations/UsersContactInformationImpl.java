package com.example.nurserypetbot.services.implementations;

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
        UsersContactInformation usersContactInformation;
        long chatId = message.chat().id();
        SendMessage result;

        try {

            usersContactInformation = Parser.tryToParseUsersInformation(message.text());
            usersContactInformation.setChatId(chatId);


        } catch (Exception ex) {
            telegramBot.execute(new SendMessage(chatId, "Wrong format"));
            return;
        }

        try {
            repository.save(usersContactInformation);

        } catch (Exception exception) {
            telegramBot.execute(new SendMessage(chatId, "This phone number or email address is already in our DB"));
            return;
        }

        result = new SendMessage(chatId, String.format("OK, your information successfully added"));
        telegramBot.execute(result);

    }
}

