//package com.example.nurserypetbot.services.implementations;
//
//import com.example.nurserypetbot.models.Photo;
//import com.example.nurserypetbot.models.UsersContactInformation;
//import com.example.nurserypetbot.repository.CatUsersContactInformationRepository;
//import com.example.nurserypetbot.repository.PhotoRepository;
//import com.example.nurserypetbot.services.services.PhotoService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Optional;
//
//
//import static java.nio.file.StandardOpenOption.CREATE_NEW;
//@Service
//public class PhotoServiceImpl implements PhotoService {
//    private final Logger logger = LoggerFactory.getLogger(PhotoServiceImpl.class);
//    private final String photosDir;
//    private final UsersContactInformationImpl usersContactInformation;
//    private final PhotoRepository photoRepository;
//    public PhotoServiceImpl(UsersContactInformationImpl usersContactInformation
//            , PhotoRepository photoRepository
//            , @Value("${path.to.photos.folder}") String photosDir) {
//        this.photosDir = photosDir;
//        this.usersContactInformation = usersContactInformation;
//        this.photoRepository = photoRepository;
//    }
//    @Override
//    public void uploadPhoto(Long userId, MultipartFile photoFile) throws IOException {
//
//        logger.info( "Был вызван метод uploadPhoto с данными - id нового владельца" + userId
//                + ". Данные фото отчета " + photoFile.getOriginalFilename() + " " + photoFile.getSize() );
//
//        Optional<UsersContactInformation> usersContactInformation = CatUsersContactInformationRepository.findById(userId);
//        Path filePath = Path.of(photosDir, usersContactInformation.get().getSurname() + ".photoReport");
//        Files.createDirectories(filePath.getParent());
//        Files.deleteIfExists(filePath);
//        try (
//                InputStream is = photoFile.getInputStream();
//                BufferedInputStream bis = new BufferedInputStream(is, 1024);
//                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
//                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
//        ) {
//            bis.transferTo(bos);
//        }
//        Photo photo = photoRepository.findByStudent_id(userId).orElse(new Photo());
//        photo.setStudent(student);
//        photo.setFilePath(filePath.toString());
//        photo.setFileSize(photoFile.getSize());
//        photo.setMediaType(photoFile.getContentType());
//        photo.setData(photoFile.getBytes());
//        photoRepository.save(photo);
//        logger.info("Метод uploadPhoto сохраняет в БД аватар студента " + photo);
//    }
//    @Override
//    public Photo readFromDB(long id) {
//        logger.info( "Был вызван метод readFromDB с данными - id аватарки " + id);
//
//        Optional<Photo> photo = photoRepository.findById(id);
//
//        Photo photoEntity = photo.orElseThrow(() -> new PhotoNotFoundException("Тhe photo is missing"));
//
//        logger.info("Метод вернул аватар студента " +  photoEntity);
//
//        return photoEntity;
//    }
//
//    @Override
//    public List<Photo> getPage(int size, int pageNumb) {
//        logger.info( "Был вызван метод getPage с количеством элементов на странице " + size + " и номером страницы " + pageNumb);
//        PageRequest page = PageRequest.of(pageNumb, size);
//        List<Photo> photos = photoRepository.findAll(page).getContent();
//        logger.info("Метод вернул список аватаров " + photos);
//        return photos;
//    }
//
//}
