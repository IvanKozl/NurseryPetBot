package com.example.nurserypetbot.controllers;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.services.interfaces.ReportService;
import com.example.nurserypetbot.services.interfaces.UsersContactInformationService;
import com.example.nurserypetbot.repository.UsersContactInformationRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import liquibase.pro.packaged.O;
import liquibase.pro.packaged.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final TelegramBot telegramBot;
    private final ReportRepository reportRepository;
    private final ReportService reportService;
    private final UsersContactInformationService usersContactInformationService;
    private final UsersContactInformationRepository userContactInformationRepository;

    public UserController(TelegramBot telegramBot, ReportRepository reportRepository, ReportService reportService, UsersContactInformationService usersContactInformationService, UsersContactInformationRepository userContactInformationRepository) {
        this.telegramBot = telegramBot;
        this.reportRepository = reportRepository;
        this.reportService = reportService;
        this.usersContactInformationService = usersContactInformationService;
        this.userContactInformationRepository = userContactInformationRepository;
    }

    @GetMapping(path = "/{userId}")
    public List<Report> findAllReports(@PathVariable long userId) {

        return reportRepository.findAllByUsersContactInformationId(userId);

    }

    @PutMapping(path = "/trial/{userId}")
    public UsersContactInformation setNewTrailPeriod(@PathVariable long userId) {
        var user = usersContactInformationService.read(userId);
        user.setTrialPeriod(LocalDate.now().plusDays(30));

        telegramBot.execute(new SendMessage(user.getChatId(), "Вам назначен испытательный период на 30 дней!"));
        return usersContactInformationService.update(user);

    }

    @PutMapping(path = "/extend/{userId},{extend}")
    public UsersContactInformation extendTrailPeriod(@PathVariable long userId, @PathVariable long extend) {

        var user = usersContactInformationService.read(userId);
        user.setTrialPeriod(user.getTrialPeriod().plusDays(extend));
        telegramBot.execute(new SendMessage(user.getChatId(), "Вам продлен испытательный период на " + extend + " дней." ));
        return usersContactInformationService.update(user);
    }

}