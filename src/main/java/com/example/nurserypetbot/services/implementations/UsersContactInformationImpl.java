package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.enums.Responses;
import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.parser.ParserReport;
import com.example.nurserypetbot.parser.ParserUserContactInfo;
import com.example.nurserypetbot.repository.CatUsersContactInformationRepository;
import com.example.nurserypetbot.repository.DogUsersContactInformationRepository;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.services.services.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.EnumSet;
import java.util.List;


@Service
public class UsersContactInformationImpl implements UsersContactInformationService {
    Responses responses;

    private final TelegramBot telegramBot;

    private final DogUsersContactInformationRepository dogUsersContactInformationRepository;

    private final CatUsersContactInformationRepository catUsersContactInformationRepository;
    private final ReportRepository reportRepository;

    public UsersContactInformationImpl(TelegramBot telegramBot,
                                       CatUsersContactInformationRepository catUsersContactInformationRepository,
                                       DogUsersContactInformationRepository dogUsersContactInformationRepository,
                                       ReportRepository reportRepository) {
        this.telegramBot = telegramBot;
        this.dogUsersContactInformationRepository = dogUsersContactInformationRepository;
        this.catUsersContactInformationRepository = catUsersContactInformationRepository;
        this.reportRepository = reportRepository;
    }

    /**
     * Addition new contact users information using {@link ParserUserContactInfo}
     * <br>
     * method {@link ParserUserContactInfo#tryToParseUsersInformation(String)}
     * <br>
     * Addition new information is repositories. Depends on pet ->
     * <br>
     * {@code dogUsersContactInformationRepository.save(usersContactInformation);}
     * <br>
     * {@code catUsersContactInformationRepository.save(usersContactInformation);}
     * <br>
     * Trying to catch exception, when user enters not unique information (<u>according to constraint in table</u>)
     * @param message
     */
    @Override
    public void addNewUsersInformation(Message message) {
        UsersContactInformation usersContactInformation;
        long chatId = message.chat().id();
        SendMessage result;

        try {

            usersContactInformation = ParserUserContactInfo.tryToParseUsersInformation(message.text().toUpperCase());
            usersContactInformation.setChatId(chatId);


        } catch (Exception ex) {
            telegramBot.execute(new SendMessage(chatId, "Wrong format"));
            return;
        }

        if (message.text().toUpperCase().startsWith("CAT")) {
            try {
                catUsersContactInformationRepository.save(usersContactInformation);

            } catch (Exception exception) {
                telegramBot.execute(new SendMessage(chatId,
                        "This phone number or email address is already in our DB," +
                                "or you forget some information :("));
                return;
            }
            result = new SendMessage(chatId, String.format("OK, your information successfully added"));
            telegramBot.execute(result);
        }
        else if (message.text().toUpperCase().startsWith("DOG")) {
            try {
                dogUsersContactInformationRepository.save(usersContactInformation);
            } catch (Exception exception) {
                telegramBot.execute(new SendMessage(chatId,
                        "This phone number or email address is already in our DB," +
                                "or you forget some information :("));
                return;
            }

            result = new SendMessage(chatId, String.format("OK, your information successfully added"));
            telegramBot.execute(result);
        }

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

        try{
            report = ParserReport.tryToParseReport(message.text().toLowerCase());
            report.setChatId(chatId);
        } catch (Exception ex){
            telegramBot.execute(new SendMessage(chatId, "Wrong format of report, please," +
                    "find the example in MENU in DAY"));
            return;
        }
        reportRepository.save(report);
        result = new SendMessage(chatId, String.format("OK, your report successfully added"));
        telegramBot.execute(result);

    }
    /**Сравнение входящий сообщений с {@link com.example.nurserypetbot.enums.Responses}
     * и отправка ответных сообщений с текстом из enum-констант.
     * Используется в методе {@link com.example.nurserypetbot.listener.TelegramBotUpdatesListener#process(List)}
     * @throws IllegalArgumentException если нет констант равных @param string
     * @param chatId, string
     */
    @Override
    public void sendResponse (long chatId, String string) {
        SendPhoto locationCats = new SendPhoto(chatId,"https://disk.yandex.ru/i/LAxMchg5O9Qdtg");
        SendPhoto locationSecurity = new SendPhoto(chatId,"https://disk.yandex.ru/i/t8Gg1fX9fxbO_Q");
        SendPhoto locationDogs = new SendPhoto(chatId,"https://disk.yandex.ru/i/p1kxCruJs8NR0w");
        String str = string.toUpperCase().replaceAll("([^a-zA-Zа-яА-Я]+)","");
        EnumSet<Responses> allMenuCommands = EnumSet.allOf(Responses.class);
        if (str.equals(Responses.values()[5].toString())) {
            SendMessage message = new SendMessage(chatId, (Responses.valueOf(str)).getResponseText());
            telegramBot.execute(message);
            telegramBot.execute(locationSecurity);
        } else if (str.equals(Responses.values()[7].toString())) {
            SendMessage message = new SendMessage(chatId, (Responses.valueOf(str)).getResponseText());
            telegramBot.execute(message);
            telegramBot.execute(locationDogs);
        } else if (str.equals(Responses.values()[26].toString())) {
            SendMessage message = new SendMessage(chatId, (Responses.valueOf(str)).getResponseText());
            telegramBot.execute(message);
            telegramBot.execute(locationCats);
        } else if (allMenuCommands.toString().contains(str)) {
            SendMessage message = new SendMessage(chatId, (Responses.valueOf(str)).getResponseText());
            telegramBot.execute(message);
        } else {
            throw new IllegalArgumentException("wrong argument!!!");
        }
    }


//    @Override
//    public void processPhotoMessage(Update update) {
//        saveRawData(update);
//        var appUser = findOrSaveAppUser(update);
//        var chatId = update.message().chat().id();
//
//        try {
//            Media photo = fileService.processPhoto(update.getMessage());
//            String link = fileService.generateLink(photo.getId(), LinkType.GET_PHOTO);
//            var answer = "Фото успешно загружено! "
//                    + "Ссылка для скачивания: " + link;
//            sendAnswer(answer, chatId);
//        } catch (UploadFileException ex) {
//            log.error(ex);
//            String error = "К сожалению, загрузка фото не удалась. Повторите попытку позже.";
//            sendAnswer(error, chatId);
//        }
//    }
//
//    public AppPhoto processPhoto(Message telegramMessage) {
//        var photoSizeCount = telegramMessage.getPhoto().size();
//        var photoIndex = photoSizeCount > 1 ? telegramMessage.getPhoto().size() - 1 : 0;
//        var telegramPhoto = telegramMessage.getPhoto().get(photoIndex);
//        var fileId = telegramPhoto.getFileId();
//        var response = getFilePath(fileId);
//        if (response.getStatusCode() == HttpStatus.OK) {
//            var persistentBinaryContent = getPersistentBinaryContent(response);
//            var transientAppPhoto = buildTransientAppPhoto(telegramPhoto, persistentBinaryContent);
//            return appPhotoDAO.save(transientAppPhoto);
//        } else {
//            throw new UploadFileException("Bad response from telegram service: " + response);
//        }
//    }
//    private BinaryContent getPersistentBinaryContent(ResponseEntity<String> response) {
//        var filePath = getFilePath(response);
//        var fileInByte = downloadFile(filePath);
//        var transientBinaryContent = BinaryContent.builder()
//                .fileAsArrayOfBytes(fileInByte)
//                .build();
//        return binaryContentDAO.save(transientBinaryContent);
//    }
//    private String getFilePath(ResponseEntity<String> response) {
//        var jsonObject = new JSONObject(response.getBody());
//        return String.valueOf(jsonObject
//                .getJSONObject("result")
//                .getString("file_path"));
//    }
//    private AppPhoto buildTransientAppPhoto(PhotoSize telegramPhoto, BinaryContent persistentBinaryContent) {
//        return AppPhoto.builder()
//                .telegramFileId(telegramPhoto.getFileId())
//                .binaryContent(persistentBinaryContent)
//                .fileSize(telegramPhoto.getFileSize())
//                .build();
//    }
//    private void saveRawData(Update update) {
//        var rawData = RawData.builder()
//                .event(update)
//                .build();
//        rawDataRepo.save(rawData);
//    }

//private ResponseEntity<String> getFilePath(String fileId) {
//    var restTemplate = new RestTemplate();
//    var headers = new HttpHeaders();
//    var request = new HttpEntity<>(headers);
//
//    return restTemplate.exchange(
//            fileInfoUri,
//            HttpMethod.GET,
//            request,
//            String.class,
//            token, fileId
//    );
//}
//private AppPhoto buildTransientAppPhoto(PhotoSize telegramPhoto, BinaryContent persistentBinaryContent) {
//    return AppPhoto.builder()
//            .telegramFileId(telegramPhoto.getFileId())
//            .binaryContent(persistentBinaryContent)
//            .fileSize(telegramPhoto.getFileSize())
//            .build();



    }


