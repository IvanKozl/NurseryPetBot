package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.parser.Parser;
import com.example.nurserypetbot.repository.CatUsersContactInformationRepository;
import com.example.nurserypetbot.repository.DogUsersContactInformationRepository;
import com.example.nurserypetbot.services.services.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;


@Service
public class UsersContactInformationImpl implements UsersContactInformationService {

    private final TelegramBot telegramBot;

    private final DogUsersContactInformationRepository dogUsersContactInformationRepository;

    private final CatUsersContactInformationRepository catUsersContactInformationRepository;

    public UsersContactInformationImpl(TelegramBot telegramBot,
                                       CatUsersContactInformationRepository catUsersContactInformationRepository,
                                       DogUsersContactInformationRepository dogUsersContactInformationRepository) {
        this.telegramBot = telegramBot;
        this.dogUsersContactInformationRepository = dogUsersContactInformationRepository;
        this.catUsersContactInformationRepository = catUsersContactInformationRepository;
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

        if (message.text().toUpperCase().startsWith("CAT")) {
            try {
                catUsersContactInformationRepository.save(usersContactInformation);

            } catch (Exception exception) {
                telegramBot.execute(new SendMessage(chatId, "This phone number or email address is already in our DB"));
                return;
            }
            result = new SendMessage(chatId, String.format("OK, your information successfully added"));
            telegramBot.execute(result);
        }
        else if (message.text().toUpperCase().startsWith("DOG")) {
            try {
                dogUsersContactInformationRepository.save(usersContactInformation);

            } catch (Exception exception) {
                telegramBot.execute(new SendMessage(chatId, "This phone number or email address is already in our DB"));
                return;
            }

            result = new SendMessage(chatId, String.format("OK, your information successfully added"));
            telegramBot.execute(result);
        }

    }
}