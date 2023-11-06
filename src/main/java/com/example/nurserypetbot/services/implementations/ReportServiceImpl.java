package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.parser.ParserReport;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.services.interfaces.ReportService;
import com.example.nurserypetbot.services.interfaces.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    private  final ParserReport parserReport;
    private final ReportRepository reportRepository;
    private final TelegramBot telegramBot;
    private final UsersContactInformationService usersContactInformationService;

    public ReportServiceImpl(ParserReport parserReport, ReportRepository reportRepository, TelegramBot telegramBot,
                             UsersContactInformationService usersContactInformationService) {
        this.parserReport = parserReport;
        this.reportRepository = reportRepository;
        this.telegramBot = telegramBot;
        this.usersContactInformationService = usersContactInformationService;
    }
    /**
     * Addition user's report using {@link ParserReport}
     * <br>
     * method {@link ParserReport#tryToParseReport(Message)}
     * <br>
     * Addition report information in repository
     * <br>
     * {@code reportRepository.save(report);}
     * @param message
     */
    @Override
    public void addReport(Message message) {

        Report report;

        try{
            report = parserReport.tryToParseReport(message);
            report.setChatId(message.chat().id());
        } catch (Exception ex){
            telegramBot.execute(new SendMessage(message.chat().id(), "Неправильный формат отчета, пожалуйста повторите \n" +
                    "Образец заполнения находится в соседнем пункте меню"));
            return;
        }
        var user = usersContactInformationService.readByChatId(message.chat().id());
        report.setUsersContactInformation(user);

        reportRepository.save(parserReport.tryToParseReport(message));
        telegramBot.execute(new SendMessage(message.chat().id(), String.format("OK, текстовый отчет добавлен. Если фото уже добавлено, значит вы сдали отчет.")));
    }
    /**
     * Find date and time of report using {@link LocalDate}
     * @param id
     * @return
     */
    @Override
    public LocalDate findDateAndTimeOfReport(long id) {
        Report report = new Report();
        LocalDate dateTime = report.getDateTime();
        return dateTime;
    }
    /**
     * Create trial period for user using
     * @param userId
     */
    @Override
    public void createTrialPeriod(long userId) {
        var user = usersContactInformationService.read(userId);
        user.setTrialPeriod(LocalDate.now().plusDays(30));
        usersContactInformationService.update(user);
    }
    /**
     * Отправляет предупреждение всем владельцам животного c испытательным сроком,
     * <br>
     * если до 21.00 ежедневный отчет был не отправлен
     * @param
     */
    @Override
    @Scheduled(cron = "0 0 21 * * *")
    public void checkDailyReport(){
        var users = usersContactInformationService.getAllUsersWithActualTrialPeriod();
        for(var user : users){
                Optional <Report> report = reportRepository.getByUsersContactInformationId(user.getId());
            if(report.isEmpty()) {
                SendMessage message = new SendMessage(user.getChatId(), "Don't forget to send you report, " +
                        "Otherwise, our volunteer will be forced to extend or even cancel your trial period :(");
                telegramBot.execute(message);
            }
        }
    }

//    @Override
//    @Scheduled(cron = "0 0/1 * * * *")
//    public void sendRemember() {
//        var users = usersContactInformationService.getAllUsersWithActualTrialPeriod();
//        for(var user : users){
//            SendMessage message = new SendMessage(user.getChatId(), "Not forget to send a report");
//            telegramBot.execute(message);
//        }
//    }

}
