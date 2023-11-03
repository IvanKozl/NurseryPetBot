package com.example.nurserypetbot.listener;

import com.example.nurserypetbot.enums.Responses;
import com.example.nurserypetbot.models.Photo;
import com.example.nurserypetbot.parser.ParserReport;
import com.example.nurserypetbot.parser.ParserUserContactInfo;
import com.example.nurserypetbot.repository.PhotoRepository;
import com.example.nurserypetbot.services.implementations.UsersContactInformationImpl;
import com.example.nurserypetbot.services.services.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
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
    private PhotoRepository photoRepository;


    public TelegramBotUpdatesListener(TelegramBot telegramBot, UsersContactInformationService service
            ,PhotoRepository photoRepository) {
        this.telegramBot = telegramBot;
        this.service = service;
        this.photoRepository = photoRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Принимает входящее сообщения из чата <br> -> поиск enum-а который ему соотвествует<br>
     * -> вывод ответного сообщения/фото (при выборе enum-а {@link Responses#SENDINFONEWCUSTOMER}
     * предусмотрен запись данных нового пользователя в БД {@link com.example.nurserypetbot.models.UsersContactInformation})<br>
     * Вывод сообщений происходит с помощью метода {@link UsersContactInformationImpl#sendResponse(long, String)},<br>
     * Запись данных о новом пользователе методом {@link UsersContactInformationImpl#addNewUsersInformation(Message)}.
     *
     * @param updates
     * @return int
     * @throws IllegalArgumentException в случае неправильной команды или несоответствия паттерну набора данных нового пользователя
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {


            logger.info("Processing update: {}", update);
            
            if (update.message().text().matches(ParserUserContactInfo.getParserInfoString())) {
                service.addNewUsersInformation(update.message());
            } else if (update.message().text().matches(ParserReport.getParserReportString())) {
                service.addReport(update.message());
            } else if (update.message().photo().getClass().equals(PhotoSize[].class)  ) {
                PhotoSize[] photoSizes = update.message().photo();
//                PhotoSize biggestPhoto = photoSizes[0];
//                for (PhotoSize photo : photoSizes) {
//                    if (photo.width() > biggestPhoto.width()) {
//                        biggestPhoto = photo;
//                    }
//                }
//                String fileId = biggestPhoto.fileId();

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
