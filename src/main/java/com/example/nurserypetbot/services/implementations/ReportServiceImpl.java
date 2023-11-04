package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.parser.ParserReport;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.services.services.ReportService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final TelegramBot telegramBot;

    public ReportServiceImpl(ReportRepository reportRepository, TelegramBot telegramBot) {
        this.reportRepository = reportRepository;

        this.telegramBot = telegramBot;
    }
    /**
     * Addition user's report using {@link ParserReport}
     * <br>
     * method {@link ParserReport#tryToParseReport(String)}
     * <br>
     * Addition report information in repository
     * <br>
     * {@code reportRepository.save(report);}
     * @param message
     */
    @Override
    public void addReport(Message message) {
            Report report;
            long chatId = message.chat().id();
            SendMessage result;

            try {
                report = ParserReport.tryToParseReport(message.text().toLowerCase());
                report.setChatId(chatId);
            } catch (Exception ex) {
                telegramBot.execute(new SendMessage(chatId, "Wrong format of report, please," +
                        "find the example in MENU in DAY"));
                return;
            }
            reportRepository.save(report);
            result = new SendMessage(chatId, String.format("OK, your report successfully added"));
            telegramBot.execute(result);
        }

    @Override
    public LocalDateTime findDateAndTimeOfReport(long id) {
        Report report = new Report();
        LocalDateTime dateTime = report.getDateTime();
        return dateTime;
    }

    @Override
    public LocalDateTime createTrailPeriod(long userId) {
        Report report = new Report();
        UsersContactInformation usersContactInformation = new UsersContactInformation();

        var firstReport = report.getDateTime();
        var trailPeriod = firstReport.plusDays(30);
        usersContactInformation.setTrailPeriod(trailPeriod);
        return trailPeriod;
    }
}
