package com.example.nurserypetbot.listener;

import com.example.nurserypetbot.enums.Responses;


import com.example.nurserypetbot.parser.ParserUserContactInfo;
import com.example.nurserypetbot.services.services.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.List;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final UsersContactInformationService service;

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      UsersContactInformationService service
                                      ) {
        this.telegramBot = telegramBot;
        this.service = service;

    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Executing new message depends on users choose using {@link Responses}
     * <br> {@link Responses#START}
     * <br> {@link Responses#MENU}
     * <br> {@link Responses#CAR}
     * <br> {@link Responses#SAFETY}
     * <br> {@link Responses#REASONS}
     * <br> {@link Responses#HELP}
     * <br> {@link Responses#ADD}
     * <br> {@link Responses#ADVISES}
     * <br> {@link Responses#DOGHELP}
     * <br> {@link Responses#NUMBER}
     * <br> {@link Responses#DATA}
     * <br> {@link Responses#REPORT}
     * <br> When user want bot to copy his/her contact information,bot uses
     * <br> {@link UsersContactInformationService#addNewUsersInformation(Message)}
     *
     * @param updates
     * @return
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);


            if (update.message().text().startsWith("/start")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.START.getResponseText());
                var result = telegramBot.execute(message);
            } else if (update.message().text().toUpperCase().startsWith("MENU")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.MENU.getResponseText());
                var result = telegramBot.execute(message);
            } else if (update.message().text().toUpperCase().startsWith("CAR")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.CAR.getResponseText());
                var result = telegramBot.execute(message);
            } else if (update.message().text().toUpperCase().startsWith("SAFETY")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.SAFETY.getResponseText());
                var result = telegramBot.execute(message);

            } else if (update.message().text().toUpperCase().startsWith("REASONS")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.REASONS.getResponseText());
                var result = telegramBot.execute(message);

            } else if (update.message().text().toUpperCase().startsWith("HELP")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.HELP.getResponseText());
                var result = telegramBot.execute(message);
            } else if (update.message().text().toUpperCase().startsWith("ADD")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.ADD.getResponseText());
                var result = telegramBot.execute(message);
            } else if (update.message().text().toUpperCase().startsWith("ADVISES")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.ADVISES.getResponseText());
                var result = telegramBot.execute(message);
            } else if (update.message().text().toUpperCase().startsWith("DOGHELP")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.DOGHELP.getResponseText());
                var result = telegramBot.execute(message);
            } else if (update.message().text().toUpperCase().startsWith("NUMBER")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.NUMBER.getResponseText());
                var result = telegramBot.execute(message);
            } else if (update.message().text().toUpperCase().startsWith("DATA")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.DATA.getResponseText());
                var result = telegramBot.execute(message);
            } else if (update.message().text().toUpperCase().startsWith("REPORT")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.REPORT.getResponseText());
                var result = telegramBot.execute(message);
            }
            else if (update.message().text().toUpperCase()
                    .matches(ParserUserContactInfo.getParserString())) {
            } else if (update.message().text().toUpperCase().startsWith("DOCUMENTS")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.DATA.getResponseText());
                var result = telegramBot.execute(message);
            }
            else if (update.message().text().toUpperCase().startsWith("RULES")) {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.DATA.getResponseText());
                var result = telegramBot.execute(message);
            }
            else if (update.message().text().toUpperCase()
                    .matches(ParserUserContactInfo.getParserString())) {
                service.addNewUsersInformation(update.message());
            }

            else {
                SendMessage message = new SendMessage(update.message().chat().id(),
                        Responses.WRONG.getResponseText());
                var result = telegramBot.execute(message);
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
