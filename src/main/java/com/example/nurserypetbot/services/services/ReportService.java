package com.example.nurserypetbot.services.services;

import com.example.nurserypetbot.models.Report;

import java.time.LocalDateTime;

public interface ReportService {
    LocalDateTime findDateAndTimeOfReport(long userId);
    LocalDateTime createTrailPeriod(long userId);

}
