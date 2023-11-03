package com.example.nurserypetbot.schedule;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.services.services.ReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class Schedule {
    private final ReportService service;
    private final ReportRepository repository;
    private final TelegramBot telegramBot;
    private final UsersContactInformation usersContactInformation;

    public Schedule(ReportService service, ReportRepository repository, TelegramBot telegramBot, UsersContactInformation usersContactInformation) {
        this.service = service;
        this.repository = repository;
        this.telegramBot = telegramBot;
        this.usersContactInformation = usersContactInformation;
    }
    @Scheduled(cron = "0 00 10 * * *")
    public void sendRemember() {
        var chatId = usersContactInformation.getChatId();
        if (!usersContactInformation.getTrailPeriod().toString().isEmpty()) {
            var response = new SendMessage(chatId, "Not forget to send a report");
            telegramBot.execute(response);
        }
    }

    @Scheduled(cron = "0 00 21 * * *")
    public void checkDailyReport(){
       var day = repository.findReportForEachUserByDay(usersContactInformation.getId(), LocalDateTime.now());
       if(day.isEmpty()){
           var response = new SendMessage(usersContactInformation.getChatId(), "You forgot to send report");
           telegramBot.execute(response);
       }
    }


}
