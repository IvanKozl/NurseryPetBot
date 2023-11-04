package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.parser.ParserReport;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.services.services.ReportService;
import com.example.nurserypetbot.services.services.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final TelegramBot telegramBot;
    private final UsersContactInformationService usersContactInformationService;

    public ReportServiceImpl(ReportRepository reportRepository, TelegramBot telegramBot,
                             UsersContactInformationService usersContactInformationService) {
        this.reportRepository = reportRepository;

        this.telegramBot = telegramBot;
        this.usersContactInformationService = usersContactInformationService;
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
    public void createTrailPeriod(long userId) {

        var user = usersContactInformationService.read(userId);
        user.setTrailPeriod(LocalDateTime.now().plusDays(30));
        usersContactInformationService.update(user);
    }
    @Scheduled(cron = "0 00 10 * * *")
    public void sendRemember() {
        var users = usersContactInformationService.getAllUsersWithActualTrailPeriod();
        for(var user : users){
            SendMessage message = new SendMessage(user.getChatId(), "Not forget to send a report");
            telegramBot.execute(message);
        }
        }
    @Scheduled(cron = "0 00 21 * * *")
    public void checkDailyReport(){
        var users = usersContactInformationService.getAllUsersWithActualTrailPeriod();
        for(var user : users){
            SendMessage message = new SendMessage(user.getChatId(), "You forgot to send a report, " +
                    "unfortunately, our volunteer will be forced to extend your trail period :(");
            telegramBot.execute(message);
        }
        }
}



