package com.example.nurserypetbot.controllers;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.services.services.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class ReportController {
    private final ReportRepository reportRepository;

    private final ReportService reportService;

    public ReportController(ReportRepository reportRepository, ReportService reportService) {
        this.reportRepository = reportRepository;

        this.reportService = reportService;
    }

    @GetMapping(path = "/{user_id}")
    public List<Report> findAllReports(@PathVariable long userId){
       return reportRepository.findAllById(Collections.singleton(userId));
    }

}
