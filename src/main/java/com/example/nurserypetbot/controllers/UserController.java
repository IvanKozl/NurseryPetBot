package com.example.nurserypetbot.controllers;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.repository.UserContactInformationRepository;
import com.example.nurserypetbot.services.services.ReportService;
import com.example.nurserypetbot.services.services.UsersContactInformationService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final ReportRepository reportRepository;

    private final ReportService reportService;
    private final UsersContactInformationService usersContactInformationService;
    private final UserContactInformationRepository userContactInformationRepository;

    public UserController(ReportRepository reportRepository, ReportService reportService, UsersContactInformationService usersContactInformationService, UserContactInformationRepository userContactInformationRepository) {
        this.reportRepository = reportRepository;

        this.reportService = reportService;
        this.usersContactInformationService = usersContactInformationService;
        this.userContactInformationRepository = userContactInformationRepository;
    }

    @GetMapping(path = "/{userId}")
    public List<Report> findAllReports(@PathVariable long userId) {

        return reportRepository.findAllByUsersContactInformationId(userId);
    }

    @PutMapping(path = "/trail/{userId}")
    public UsersContactInformation setNewTrailPeriod(@PathVariable long userId) {
        var user = usersContactInformationService.read(userId);
        user.setTrailPeriod(LocalDateTime.now().plusDays(30));
        return usersContactInformationService.update(user);

    }

    @PutMapping(path = "/extend/{userId}")
    public UsersContactInformation extendTrailPeriod(@PathVariable long userId) {

        var user = usersContactInformationService.read(userId);
        user.setTrailPeriod(user.getTrailPeriod().plusDays(14));
        return usersContactInformationService.update(user);
    }

}
