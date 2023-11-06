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
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PhotoServiceImpl implements PhotoService {
    private UsersContactInformationServiceImpl usersContactInformationService;
    private UsersContactInformationRepository usersContactInformationRepository;
    private ReportRepository reportRepository;
    private TelegramBot telegramBot;
    private final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);
    public PhotoServiceImpl(UsersContactInformationServiceImpl usersContactInformationService, ReportRepository reportRepository, TelegramBot telegramBot) {
        this.usersContactInformationService = usersContactInformationService;
        this.reportRepository = reportRepository;
        this.telegramBot = telegramBot;
    }

    /** Проверяет наличие фото в входящем сообщении и добавляет фото к отчету.
     * <br>Создает новый обьект отчета, если ранее текстовая часть отчета не была добавлен
     * через метод <br>{@link ReportServiceImpl#addReport(Message)},
     * в противном случае проставляет недостающие поля обьекта {@code Report}.
     * @param message
     * @throws IllegalArgumentException если получены пустые fileId и fileUniqueId
     * @throws NoSuchElementException если не получилось вернуть обьект пользователя из {@link UsersContactInformationRepository#findByChatId(long)}
     */
    @Override
    public void processPhoto(Message message) {
//        LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        LocalDate currentDate = LocalDate.now();
        Report report = reportRepository.findByDateTimeAndChatId(currentDate, message.chat().id()).orElse(new Report());
        report.setChatId(message.chat().id());


        List<UsersContactInformation> newOwnerList = usersContactInformationService.getAllUsersWithActualTrialPeriod();
        UsersContactInformation ownerUnderTrial = new UsersContactInformation();
        for (var newOwner : newOwnerList) {
            if (newOwner.getChatId() == message.chat().id()) {
                ownerUnderTrial = newOwner;
            }
        }
        PhotoSize firstIncPhoto = message.photo()[0];
        String fileId = firstIncPhoto.fileId();
        String fileUniqId = firstIncPhoto.fileUniqueId();

        if (!fileId.isEmpty() && !fileUniqId.isEmpty()) {
            report.setFotoCheck(true);
            report.setUsersContactInformation(ownerUnderTrial);
            reportRepository.save(report);
            telegramBot.execute(new SendMessage(message.chat().id(), "Фото добавлено к отчету! \n" +
                    "Теперь добавьте текстовую часть отчета, если вы еще этого не делали и тогда ежедневный отчет будет считаться выполненным."));
            logger.info( "Создали репорт, проставили \"истина\" в фотоЧек в БД");
            if (!(report.getBehavior()==null) && !(report.getFood()==null) && !(report.getFeel()==null)) {
                report.setReportCheck(true);
                reportRepository.save(report);
                telegramBot.execute(new SendMessage(message.chat().id(),
                        "Отчет принят. Вы молодец!"));
                logger.info( "Проверили добавление текстового отчета, проставили true в репортЧек в БД");
            }
        } else {
            throw new IllegalArgumentException("Фото НЕ отправлено!!!");
        }
    }
    
    //  Update update = response.updates().get(0);
//PhotoSize[] photoSizes = update.message().photo();
//PhotoSize biggestPhoto = photoSizes[0];
//for (PhotoSize photo : photoSizes) {
//    if (photo.width() > biggestPhoto.width()) {
//        biggestPhoto = photo;
//    }
//}
//String fileId = biggestPhoto.fileId();
//    ________________________________________________
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
