package com.example.nurserypetbot.listener;

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

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            // Common greetind
            if(update.message().text().equals("/start")){
                long chatId = update.message().chat().id();
                telegramBot.execute(new SendMessage(chatId, "Привет, странник."));
            }
            //Common help with notification format
            if(update.message().text().equals("/help")){
                long chatId = update.message().chat().id();
                telegramBot.execute(new SendMessage(chatId, "Для создания напоминания введите сообщение в" +
                        " таком формате: дд.мм.гггг чч:мм текст_напоминания"));
            }
            String textMessage = update.message().text();
            Long chatId = update.message().chat().id();
            //New notifications method
            newNotification(chatId,textMessage);

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    public void newNotification(Long chatId, String textMessage){
        String dateAndTime;
        String notificationMessage;
        LocalDateTime parseDateAndTime;
        Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)(.+)");
        Matcher matcher = pattern.matcher(textMessage);
        if (matcher.matches()){
            dateAndTime = matcher.group(1);
            notificationMessage = matcher.group(3);
        }
        else {
            telegramBot.execute(new SendMessage(chatId, "Возможно, вы ошиблись при вводе. Нажмите /help чтобы получить подсказку."));
            return;
        }
        try {
            parseDateAndTime = LocalDateTime.parse(dateAndTime, DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm"));
        }
        catch (DateTimeException exception){
            telegramBot.execute(new SendMessage(chatId,"Неверно введена дата."));
            return;
        }
        notifictionsRepository.save(new Notification(chatId,notificationMessage,parseDateAndTime));
    }

    //Looking and sending notification for now
    private void sendNotificationToChat (List<ActualNotification> nList){
        if(!nList.isEmpty()){
            for (var n:nList){
                SendMessage messageToChat = new SendMessage(n.chatId(),n.message());
                telegramBot.execute(messageToChat);
            }
        }
    }
    @Scheduled(cron = "0 0/1 * * * *")
    private void lookingForNotification (){
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<ActualNotification> nList;
        nList = notifictionsRepository.getMessageForNowDate(now);
        sendNotificationToChat(nList);
    }



}
