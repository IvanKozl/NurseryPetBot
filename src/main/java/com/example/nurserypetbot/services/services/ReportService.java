package com.example.nurserypetbot.services.services;

import com.example.nurserypetbot.models.Report;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.response.SendResponse;

import java.time.LocalDateTime;

public interface ReportService {
    void addReport(Message message);
    LocalDateTime findDateAndTimeOfReport(long userId);
    void createTrailPeriod(long userId);


}
