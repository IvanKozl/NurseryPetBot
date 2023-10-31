package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.parser.ParserUserContactInfo;
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

    /**
     * Addition new contact users information using {@link ParserUserContactInfo}
     * <br>
     * method {@link ParserUserContactInfo#tryToParseUsersInformation(String)}
     * <br>
     * Addition new information is repositories. Depends on pet ->
     * <br>
     * {@code dogUsersContactInformationRepository.save(usersContactInformation);}
     * <br>
     * {@code catUsersContactInformationRepository.save(usersContactInformation);}
     * <br>
     * Trying to catch exception, when user enters not unique information (<u>according to constraint in table</u>)
     * @param message
     */
    @Override
    public void addNewUsersInformation(Message message) {
        UsersContactInformation usersContactInformation;
        long chatId = message.chat().id();
        SendMessage result;

        try {

            usersContactInformation = ParserUserContactInfo.tryToParseUsersInformation(message.text().toUpperCase());
            usersContactInformation.setChatId(chatId);


        } catch (Exception ex) {
            telegramBot.execute(new SendMessage(chatId, "Wrong format"));
            return;
        }

        if (message.text().toUpperCase().startsWith("CAT")) {
            try {
                catUsersContactInformationRepository.save(usersContactInformation);

            } catch (Exception exception) {
                telegramBot.execute(new SendMessage(chatId,
                        "This phone number or email address is already in our DB," +
                                "or you forget some information :("));
                return;
            }
            result = new SendMessage(chatId, String.format("OK, your information successfully added"));
            telegramBot.execute(result);
        }
        else if (message.text().toUpperCase().startsWith("DOG")) {
            try {
                dogUsersContactInformationRepository.save(usersContactInformation);
            } catch (Exception exception) {
                telegramBot.execute(new SendMessage(chatId,
                        "This phone number or email address is already in our DB," +
                                "or you forget some information :("));
                return;
            }

            result = new SendMessage(chatId, String.format("OK, your information successfully added"));
            telegramBot.execute(result);
        }

    }
}