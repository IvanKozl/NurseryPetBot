package com.example.nurserypetbot.listener;

import com.example.nurserypetbot.KeyboardUtils;
import com.example.nurserypetbot.models.ActualNotification;
import com.example.nurserypetbot.models.Notification;
import com.example.nurserypetbot.repository.NotifictionsRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import javax.annotation.PostConstruct;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private NotifictionsRepository notifictionsRepository;

    @Autowired
    private KeyboardUtils keyboardUtils;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Первичная <b> кнопка </b> запуска телеграмм-бота
     *
     * @param updates обновление пустой страницы - неизменяемый параметр.
     * @return вернет приветственное сообщение
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            if(update.message().text().equals("/start")){
                long chatId = update.message().chat().id();
                telegramBot.execute(new SendMessage(chatId, "Привет, странник.", keyboardUtils.getKeyboard()));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
