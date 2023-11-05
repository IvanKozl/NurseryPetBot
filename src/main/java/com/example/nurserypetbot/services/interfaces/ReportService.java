package com.example.nurserypetbot.services.interfaces;
import com.pengrad.telegrambot.model.Message;
import org.springframework.scheduling.annotation.Scheduled;


public interface ReportService {

    void addReport(Message message);

    void createTrailPeriod(long userId);

    //  Сделать "мягкую" напоминалку, с использованием ЕНАМ.
//    @Scheduled(cron = "0 00 10 * * *")
//    void sendRemember();

    @Scheduled(cron = "0 00 21 * * *")
    void checkDailyReport();
}
