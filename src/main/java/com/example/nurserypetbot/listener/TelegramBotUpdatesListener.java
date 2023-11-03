package com.example.nurserypetbot.listener;

import com.example.nurserypetbot.enums.Responses;

import com.example.nurserypetbot.services.implementations.UsersContactInformationImpl;
import com.example.nurserypetbot.services.services.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;

import com.pengrad.telegrambot.request.SendMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private TelegramBot telegramBot;

    private UsersContactInformationService service;


    public TelegramBotUpdatesListener(TelegramBot telegramBot, UsersContactInformationService service) {
        this.telegramBot = telegramBot;
        this.service = service;

    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

     /**Принимает входящее сообщения из чата <br> -> поиск enum-а который ему соотвествует<br>
     * -> вывод ответного сообщения/фото (при выборе enum-а {@link Responses#SENDINFONEWCUSTOMER}
      * предусмотрен запись данных нового пользователя в БД {@link com.example.nurserypetbot.models.UsersContactInformation})<br>
      * Вывод сообщений происходит с помощью метода {@link UsersContactInformationImpl#sendResponse(long, String)},<br>
      * Запись данных о новом пользователе методом {@link UsersContactInformationImpl#addNewUsersInformation(Message)}.
      *
     * @throws IllegalArgumentException в случае неправильной команды или несоответствия паттерну набора данных нового пользователя
     * @param updates
     * @return int
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            final boolean matches = update.message().text().matches("(\\w+)(\\s)([A-Za-zА-Яа-я]+)(\\s)([A-Za-zА-Яа-я]+)(\\s)(\\d+)(\\s)(\\d{11})(\\s+)([A-Za-z\\d@\\.]+)");
            logger.info("Processing update: {}", update);
            if (matches) {
                    service.addNewUsersInformation(update.message());
            } else {
                    try {

                        service.sendResponse(update.message().chat().id(), update.message().text());

                    } catch (Exception e) {
                        SendMessage result = new SendMessage(update.message().chat().id(), "wrong argument wrong!!!");
                        telegramBot.execute(result);
                    }
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
