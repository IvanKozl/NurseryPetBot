package com.example.nurserypetbot.parser;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.repository.ReportRepository;
import com.pengrad.telegrambot.model.Message;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParserReport {
    public ReportRepository reportRepository;

    public ParserReport(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report tryToParseReport(Message message) {

        LocalDate currentDate = LocalDate.now();
        Report report = reportRepository.findByDateTimeAndChatId(currentDate, message.chat().id()).orElse(new Report());
        Pattern pattern = Pattern.compile
                ("([рацион]+\\s.+)(\\s)([самочувствие]+\\s.+)(\\s)([поведение]+(\\s).+)");

        Matcher matcher = pattern.matcher(message.text().toLowerCase());

        if (matcher.matches()) {
            report.setFood(matcher.group(1));
            report.setFeel(matcher.group(3));
            report.setBehavior(matcher.group(5));
            report.setDateTime(LocalDate.now());
            report.setChatId(message.chat().id());
            if (report.isFotoCheck()) {
                report.setReportCheck(true);
            }
        } else {
            throw new IllegalArgumentException("Wrong format, please, try again." +
                    "If you have some problems, please, find MENU and instructions ");
        }
        return report;
    }

}





