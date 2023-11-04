package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.enums.Responses;
import com.example.nurserypetbot.models.Photo;
import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.parser.ParserReport;
import com.example.nurserypetbot.parser.ParserUserContactInfo;
import com.example.nurserypetbot.repository.CatUsersContactInformationRepository;
import com.example.nurserypetbot.repository.DogUsersContactInformationRepository;
import com.example.nurserypetbot.repository.PhotoRepository;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.services.services.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;


@Service
public class UsersContactInformationServiceImpl implements UsersContactInformationService {

    private final TelegramBot telegramBot;

    private final DogUsersContactInformationRepository dogUsersContactInformationRepository;

    private final CatUsersContactInformationRepository catUsersContactInformationRepository;
    private final ReportRepository reportRepository;
    private final PhotoRepository photoRepository;

    public UsersContactInformationServiceImpl(TelegramBot telegramBot,
                                              CatUsersContactInformationRepository catUsersContactInformationRepository,
                                              DogUsersContactInformationRepository dogUsersContactInformationRepository,
                                              ReportRepository reportRepository, PhotoRepository photoRepository) {
        this.telegramBot = telegramBot;
        this.dogUsersContactInformationRepository = dogUsersContactInformationRepository;
        this.catUsersContactInformationRepository = catUsersContactInformationRepository;
        this.reportRepository = reportRepository;
        this.photoRepository = photoRepository;
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

    public void processPhoto(Message message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        PhotoSize[] photoSizes = message.photo();
        PhotoSize biggestPhoto = photoSizes[0];
        for (PhotoSize photo : photoSizes) {
            if (photo.width() > biggestPhoto.width()) {
                biggestPhoto = photo;
            }
        }
        String fileId = biggestPhoto.fileId();
        String fileUniqId = biggestPhoto.fileUniqueId();
        telegramBot.execute(new SendMessage(message.chat().id(), fileUniqId));
        telegramBot.execute(new SendMessage(message.chat().id(), fileId));
        if (message.text().isEmpty()) {

        }
//
//        if (!fileId.isEmpty() && !fileUniqId.isEmpty()) {
//            try {
//                Report report = reportRepository.findByDate(dateFormat.parse(message.date().toString())).orElse(new Report());
//            } catch (Exception e) {
//                е
//            }
//            report.setFotoCheck(true);
//            reportRepository.save(report);
//            if (!(report.getBehavior().isEmpty()) && !(report.getFood().isEmpty()) && !(report.getFeel().isEmpty())) {
//                report.setReportCheck(true);
//                reportRepository.save(report);
//            }
//        } else {
//            throw new IllegalArgumentException("Фото НЕ отправлено!!!");
//        }
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
//    @Value("${service.file_info.uri}")
//    private String fileInfoUri;
//
//    @Value("${service.file_storage.uri}")
//    private String fileStorageUri;
//    token:
//    service:
//    file_info:
//    uri: https://api.telegram.org/bot{token}/getFile?file_id={fileId}
//    file_storage:

//    uri: https://api.telegram.org/file/bot{token}/{filePath}


//        var photoSizeCount = telegramMessage.photo().size();
//        var photoIndex = photoSizeCount > 1 ? telegramMessage.getPhoto().size() - 1 : 0;
//        var telegramPhoto = telegramMessage.getPhoto().get(photoIndex);
//        var fileId = telegramPhoto.getFileId();

//    @Value("${service.file_info.uri}")
//    private String fileInfoUri;
//
//    @Value("${service.file_storage.uri}")
//    private String fileStorageUri;
//    token:
//    service:
//    file_info:
//    uri: https://api.telegram.org/bot{token}/getFile?file_id={fileId}
//    file_storage:
//    uri: https://api.telegram.org/file/bot{token}/{filePath}
//
//        PhotoSize[] photoSizes = message.photo();
//        PhotoSize biggestPhoto = photoSizes[0];
//        for (PhotoSize photo : photoSizes) {
//            if (photo.width() > biggestPhoto.width()) {
//                biggestPhoto = photo;
//            }
//        }
//        String fileId = biggestPhoto.fileId();
//        ------------------------------------------------------------------
//        public void uploadAvatar (Long reportid, MultipartFile photoFile) throws IOException {
//
//            Report report = reportService.findReport(reportId);//
//            Path filePath = Path.of(photosDir, photo.getName() + ".photo");//формируем путь куда сохраняем
//            Files.createDirectories(filePath.getParent());//создаем директорию под файл
//            Files.deleteIfExists(filePath);//удаляем если в директории что то есть
//
//            try (
//                    InputStream is = photoFile.getInputStream(); // превращаем загружаемый файл (мультьпарт!!) в входящий поток
//                    BufferedInputStream bis = new BufferedInputStream(is, 1024); // поток превращаем в буферный поток с
////                  с указанием размера. В обоих случаях кидаем в переменную
//                    OutputStream os = Files.newOutputStream(filePath, CREATE_NEW); // создаем исходящий поток в filePath и создаем
////                    новый файл
//                    BufferedOutputStream bos = new BufferedOutputStream(os, 1024); // исходящий поток превращаем в буферный
////                    исходящий с регулировкой размера
//            ) {
//                bis.transferTo(bos); // буферный входящий отправляем буферный исходящий,
//            }
//
//            //Если найти byte[] от фотки, то затем тупо используем код ниже
//            Photo photo = photoRepository.findByReport_id(report_Id).orElse(new Photo());// создаем новый или находим существующий
////        файл
//            photo.setReport(report);// цепляем репорт к фото, но в базе будет только айди репорта
//            photo.setFilePath(filePath.toString()); //ясно
//            photo.setFileSize(photoFile.getSize()); //ясно
//            photo.setMediaType(photoFile.getContentType());//
//            photo.setData(photoFile.getBytes());
//            photoRepository.save(photo);
//            logger.info("Метод uploadPhoto сохраняет в БД аватар студента " + photo);
//        }
//        ---------------------------------------------------------------------
//
//                var response = getFilePath(fileId);//достаем путь к фотку сохраненной?
//        if (response.getStatusCode() == HttpStatus.OK) {
//            byte[] persistentBinaryContent = getPersistentBinaryContent(response);
//            var transientAppPhoto = buildTransientAppPhoto(telegramPhoto, persistentBinaryContent);//сделать вариацию с массивом байтов в параметре
//            return PhotoRepository.save(transientAppPhoto);
//        } else {
//            throw new UploadFileException("Bad response from telegram service: " + response);
//        }
//
//        private byte[] getPersistentBinaryContent (ResponseEntity < String > response) {
//            var filePath = getFilePath(response);
//            var fileInByte = downloadFile(filePath);
//            var transientBinaryContent = BinaryContent.builder()
//                    .fileAsArrayOfBytes(fileInByte)
//                    .build();
//            return binaryContentDAO.save(transientBinaryContent);
//        }
//        private ResponseEntity<String> getFilePath (String fileId){
//            var restTemplate = new RestTemplate();
//            var headers = new HttpHeaders();
//            var request = new HttpEntity<>(headers);
//
//            return restTemplate.exchange(
//                    fileInfoUri,
//                    HttpMethod.GET,
//                    request,
//                    String.class,
//                    token, fileId
//            );
//        }
//        private String getFilePath (ResponseEntity < String > response) {
//            var jsonObject = new JSONObject(response.getBody());
//            return String.valueOf(jsonObject
//                    .getJSONObject("result")
//                    .getString("file_path"));
//        }
//        private byte[] downloadFile(String filePath) {
//            var fullUri = fileStorageUri.replace("{token}", token)  // создаем строку для ЮРЛ, по которому находится подгруженное фото
//                    .replace("{filePath}", filePath);               // предварительно заменяя "заглушки" токена и пути к файлу на актуальные значения
//            URL urlObj = null;
//            try {
//                urlObj = new URL(fullUri);                                 //строка становится ЮРЛ официально
//            } catch (MalformedURLException e) {
//                throw new UploadFileException(e);
//            }
//
//            //TODO подумать над оптимизацией
//            try (InputStream is = urlObj.openStream()) {                    // создает поток к ЮРЛ, возвращает поток байтов
//                return is.readAllBytes();                                   // поток байтов возвращается в виде массива
//            } catch (IOException e) {
//                throw new UploadFileException(urlObj.toExternalForm(), e);// выбрасывает ошибку с 2-мя параметрами в виде строки (созданный из ЮРЛ) и собственно ошибки
//            }
//        }
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
//        private AppPhoto buildTransientAppPhoto (PhotoSize telegramPhoto, BinaryContent persistentBinaryContent){
//            return AppPhoto.builder()
//                    .telegramFileId(telegramPhoto.getFileId())
//                    .binaryContent(persistentBinaryContent)
//                    .fileSize(telegramPhoto.getFileSize())
//                    .build();
//        }
    }



