package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.repository.UsersContactInformationRepository;
import com.example.nurserypetbot.services.interfaces.PhotoService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class PhotoServiceImpl implements PhotoService {
    private UsersContactInformationServiceImpl usersContactInformationService;
    private ReportRepository reportRepository;
    private TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);

    public PhotoServiceImpl(UsersContactInformationServiceImpl usersContactInformationService, ReportRepository reportRepository, TelegramBot telegramBot) {
        this.usersContactInformationService = usersContactInformationService;
        this.reportRepository = reportRepository;
        this.telegramBot = telegramBot;
    }

    /**
     * Проверяет наличие фото в входящем сообщении и добавляет фото к отчету.
     * <br>Создает новый обьект отчета, если ранее текстовая часть отчета не была добавлен
     * через метод <br>{@link ReportServiceImpl#addReport(Message)},
     * в противном случае проставляет недостающие поля обьекта {@code Report}.
     *
     * @param message
     * @throws IllegalArgumentException если получены пустые fileId и fileUniqueId
     * @throws NoSuchElementException   если не получилось вернуть обьект пользователя из {@link UsersContactInformationRepository#findByChatId(long)}
     */
    @Override
    public void processPhoto(Message message) {

        LocalDate currentDate = LocalDate.now();
        Report report = reportRepository.findByDateTimeAndChatId(currentDate, message.chat().id()).orElse(new Report());

        report.setChatId(message.chat().id());
        report.setDateTime(LocalDate.now());
        UsersContactInformation ownerUnderTrial = usersContactInformationService.readByChatId(message.chat().id());

        PhotoSize[] photoSizes = message.photo();
        PhotoSize biggestPhoto = photoSizes[0];
        for (PhotoSize photo : photoSizes) {
            if (photo.width() > biggestPhoto.width()) {
                biggestPhoto = photo;
            }
        }

        String fileId = biggestPhoto.fileId();
        String fileUniqId = biggestPhoto.fileUniqueId();

        if (!fileId.isEmpty() && !fileUniqId.isEmpty()) {
            report.setFotoCheck(true);
            report.setUsersContactInformation(ownerUnderTrial);
            reportRepository.save(report);
            telegramBot.execute(new SendMessage(message.chat().id(), "Фото добавлено к отчету! \n" +
                    "Теперь добавьте текстовую часть отчета, если вы еще этого не делали и тогда ежедневный отчет будет считаться выполненным."));
            logger.info("Создали репорт, проставили \"истина\" в фотоЧек в БД");
            if (!(report.getBehavior() == null) && !(report.getFood() == null) && !(report.getFeel() == null)) {
                report.setReportCheck(true);
                reportRepository.save(report);
                telegramBot.execute(new SendMessage(message.chat().id(),
                        "Отчет принят. Вы молодец!"));
                logger.info("Проверили добавление текстового отчета, проставили true в репортЧек в БД");
            }
        } else {
            throw new IllegalArgumentException("Фото НЕ отправлено!!!");
        }
    }


}
