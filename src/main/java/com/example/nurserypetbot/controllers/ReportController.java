package com.example.nurserypetbot.controllers;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.services.services.ReportService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class ReportController {
    private final ReportRepository reportRepository;
    private final Report report;
    private final ReportService reportService;
    private final UsersContactInformation usersContactInformation;

    public ReportController(ReportRepository reportRepository, Report report, ReportService reportService, UsersContactInformation usersContactInformation) {
        this.reportRepository = reportRepository;
        this.report = report;
        this.reportService = reportService;
        this.usersContactInformation = usersContactInformation;
    }

    @GetMapping(path = "/{id}")
    public List<Report> findAllReports(@PathVariable long userId){
       return reportRepository.findAll();
    }

}
