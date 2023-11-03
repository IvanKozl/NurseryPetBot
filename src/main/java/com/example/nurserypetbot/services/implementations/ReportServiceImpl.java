package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.services.services.ReportService;

import java.time.LocalDateTime;

public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UsersContactInformation usersContactInformation;
    private final Report report;

    public ReportServiceImpl(ReportRepository reportRepository, UsersContactInformation usersContactInformation, Report report) {
        this.reportRepository = reportRepository;
        this.usersContactInformation = usersContactInformation;
        this.report = report;
    }

    @Override
    public LocalDateTime findDateAndTimeOfReport(long id) {
        Report report = new Report();
        LocalDateTime dateTime = report.getDateTime();
        return dateTime;
    }

    @Override
    public LocalDateTime createTrailPeriod(long userId) {
        var firstReport = report.getDateTime();
        var trailPeriod = firstReport.plusDays(30);
        usersContactInformation.setTrailPeriod(trailPeriod);
        return trailPeriod;
    }
}
