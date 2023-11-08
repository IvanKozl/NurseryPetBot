package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.enums.Responses;
import com.example.nurserypetbot.models.UsersContactInformation;

import com.example.nurserypetbot.parser.ParserUserContactInfo;
import com.example.nurserypetbot.repository.*;
import com.example.nurserypetbot.services.interfaces.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UsersContactInformationServiceImpl implements UsersContactInformationService {

    private final TelegramBot telegramBot;
    private final UsersContactInformationRepository userContactInformationRepository;

    public UsersContactInformationServiceImpl(TelegramBot telegramBot, UsersContactInformationRepository userContactInformationRepository) {
        this.telegramBot = telegramBot;
        this.userContactInformationRepository = userContactInformationRepository;
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
     *
     * @param message
     */
    @Override
    public void addNewUsersInformation(Message message) {
        UsersContactInformation usersContactInformation;
        long chatId = message.chat().id();
        SendMessage result;

        try {
            usersContactInformation = ParserUserContactInfo.tryToParseUsersInformation(message.text());
            usersContactInformation.setChatId(chatId);
        } catch (Exception ex) {
            telegramBot.execute(new SendMessage(chatId, "Неправильный формат. Пожалуйста повторите ввод информации, согласно вышеуказанному образцу."));
            return;
        }

        if (message.text().toUpperCase().startsWith("CAT")) {
            try {
                userContactInformationRepository.save(usersContactInformation);
            } catch (Exception exception) {
                telegramBot.execute(new SendMessage(chatId,
                        "Этот номер телефона или email уже зарегистрированы в нашей базе данных. " ));
                return;
            }
            result = new SendMessage(chatId, String.format("Ок, Ваша информация успешно добавлена!"));
            telegramBot.execute(result);

        } else if (message.text().toUpperCase().startsWith("DOG")) {
            try {
                userContactInformationRepository.save(usersContactInformation);
            } catch (Exception exception) {
                telegramBot.execute(new SendMessage(chatId,
                        "Этот номер телефона или email уже зарегистрированы в нашей базе данных. "));
                return;
            }

            result = new SendMessage(chatId, String.format("Ок, Ваша информация успешно добавлена! "));
            telegramBot.execute(result);
        }

    }
    /**
     * Сравнение входящий сообщений с {@link com.example.nurserypetbot.enums.Responses}
     * и отправка ответных сообщений с текстом из enum-констант.
     * Используется в методе {@link com.example.nurserypetbot.listener.TelegramBotUpdatesListener#process(List)}
     *
     * @param chatId, string
     * @throws IllegalArgumentException если нет констант равных @param string
     */
    @Override
    public void sendResponse(long chatId, String string) {
        SendPhoto exmapmleCatReport = new SendPhoto(chatId, "https://disk.yandex.ru/i/wt8uLZN2bkAQ-A");
        SendPhoto locationCats = new SendPhoto(chatId, "https://disk.yandex.ru/i/LAxMchg5O9Qdtg");
        SendPhoto locationSecurity = new SendPhoto(chatId, "https://disk.yandex.ru/i/t8Gg1fX9fxbO_Q");
        SendPhoto locationDogs = new SendPhoto(chatId, "https://disk.yandex.ru/i/p1kxCruJs8NR0w");
        String str = string.replaceAll("([^a-zA-Zа-яА-Я]+)", "").toUpperCase();
        EnumSet<Responses> allMenuCommands = EnumSet.allOf(Responses.class);
        if (str.equals(Responses.values()[5].toString())) {
            SendMessage message = new SendMessage(chatId, (Responses.valueOf(str)).getResponseText());
            telegramBot.execute(message);
            telegramBot.execute(locationSecurity);
        } else if (str.equals(Responses.values()[7].toString())) {
            SendMessage message = new SendMessage(chatId, (Responses.valueOf(str)).getResponseText());
            telegramBot.execute(message);
            telegramBot.execute(locationDogs);
        } else if (str.equals(Responses.values()[26].toString())) {
            SendMessage message = new SendMessage(chatId, (Responses.valueOf(str)).getResponseText());
            telegramBot.execute(message);
            telegramBot.execute(locationCats);
        } else if (str.equals(Responses.values()[21].toString())) {
            SendMessage message = new SendMessage(chatId, (Responses.valueOf(str)).getResponseText());
            telegramBot.execute(message);
            telegramBot.execute(exmapmleCatReport);
        } else if (allMenuCommands.toString().contains(str)) {
            SendMessage message = new SendMessage(chatId, (Responses.valueOf(str)).getResponseText());
            telegramBot.execute(message);
        } else {
            throw new IllegalArgumentException("wrong argument!!!");
        }
    }
    /**
     * Reading user's contact information using method
     * {@link UsersContactInformationRepository#findById(Object)}
     *
     * @param user_id
     * @return UsersContactInformation
     */
    @Override
    public UsersContactInformation read(long user_id) {
        return userContactInformationRepository.findById(user_id).orElseThrow();
    }
    /**
     * Reading user's contact information using method
     * {@link UsersContactInformationRepository#findByChatId(long)}
     *
     * @param chatId
     * @return UsersContactInformation
     */
    @Override
    public UsersContactInformation readByChatId(long chatId) {
        return userContactInformationRepository.findByChatId(chatId).orElseThrow();
    }

    /**
     * Updating user's contact information
     *
     * @param usersContactInformation
     * @return UsersContactInformation
     */
    @Override
    public UsersContactInformation update(UsersContactInformation usersContactInformation) {
        return userContactInformationRepository.save(usersContactInformation);
    }

    /**
     * Finding users with actual trial period
     * @return List<UsersContactInformation>
     */
    @Override
    public List<UsersContactInformation> getAllUsersWithActualTrialPeriod() {
        var result = userContactInformationRepository.findAll();
        return result.stream().filter(u -> u.getTrialPeriod() != null)
                .collect(Collectors.toList());
    }
}



