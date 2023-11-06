package com.example.nurserypetbot.services.interfaces;
import com.pengrad.telegrambot.model.Message;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;


public interface ReportService {

    void addReport(Message message);


    //  Сделать "мягкую" напоминалку, с использованием ЕНАМ.
//    @Scheduled(cron = "0 00 10 * * *")
//    void sendRemember();

    LocalDate findDateAndTimeOfReport(long id);

    void createTrialPeriod(long userId);

    @Scheduled(cron = "0 00 21 * * *")
    void checkDailyReport();
}
