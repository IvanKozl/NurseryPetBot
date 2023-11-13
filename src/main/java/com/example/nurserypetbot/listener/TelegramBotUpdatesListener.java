package com.example.nurserypetbot.listener;

import com.example.nurserypetbot.enums.Responses;
import com.example.nurserypetbot.parser.ParserUserContactInfo;
import com.example.nurserypetbot.services.implementations.PhotoServiceImpl;
import com.example.nurserypetbot.services.implementations.UsersContactInformationServiceImpl;
import com.example.nurserypetbot.services.interfaces.ReportService;
import com.example.nurserypetbot.services.interfaces.UsersContactInformationService;
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
    private ReportService reportService;
    private PhotoServiceImpl photoService;


    public TelegramBotUpdatesListener(TelegramBot telegramBot, UsersContactInformationService service, ReportService reportService, PhotoServiceImpl photoService) {
        this.telegramBot = telegramBot;
        this.service = service;
        this.reportService = reportService;
        this.photoService = photoService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Принимает входящее сообщения из чата <br> -> поиск enum-а который ему соотвествует<br>
     * -> вывод ответного сообщения/фото (при выборе enum-а {@link Responses#SENDINFONEWCUSTOMER}
     * предусмотрен запись данных нового пользователя в БД {@link com.example.nurserypetbot.models.UsersContactInformation})<br>
     * Вывод сообщений происходит с помощью метода {@link UsersContactInformationServiceImpl#sendResponse(long, String)},<br>
     * Запись данных о новом пользователе методом {@link UsersContactInformationServiceImpl#addNewUsersInformation(Message)}.
     *
     * @param updates
     * @return int
     * @throws IllegalArgumentException в случае неправильной команды или несоответствия паттерну набора данных нового пользователя
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
//

            logger.info("Processing update: {}", update);
            if (update.message().text() != null) {
                if (update.message().text().matches(ParserUserContactInfo.getParserInfoString())) {
                    service.addNewUsersInformation(update.message());
                    logger.info("Добавили инфо потенциального владельца");
                } else if (update.message().text().toLowerCase().matches("([рацион]+\\s.+)(\\s)([самочувствие]+\\s.+)(\\s)([поведение]+(\\s).+)")) {
                    reportService.addReport(update.message());
                    logger.info("Отчет сохранен  в БД");
                } else {
                    try {
                        service.sendResponse(update.message().chat().id(), update.message().text().toUpperCase());
                    } catch (Exception e) {
                        SendMessage result = new SendMessage(update.message().chat().id(), "Неправильная команда, повторите!!!");
                        telegramBot.execute(result);
                    }
                }
            }

            if (update.message().photo() != null && update.message().photo().length > 0) {
                try {
                    photoService.processPhoto(update.message());
                    logger.info("ФОТО ДОБАВЛЕНО");
                } catch (Exception e) {
                    SendMessage result = new SendMessage(update.message().chat().id(), "Фото не добавлено!!!");
                    telegramBot.execute(result);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;

    }
}
