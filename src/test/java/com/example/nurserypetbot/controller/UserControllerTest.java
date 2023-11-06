package com.example.nurserypetbot.controller;

import com.example.nurserypetbot.enums.PetShelter;
import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.repository.ReportRepository;
import com.example.nurserypetbot.repository.UserContactInformationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    UserContactInformationRepository userContactInformationRepository;

    @Autowired
    ReportRepository reportRepository;


    @AfterEach
    void afterEach() {
        reportRepository.deleteAll();
        userContactInformationRepository.deleteAll();
    }

    String url = "http://localhost:";


    @Test
    void readAllReportsOfUser__returnListOfReports() {
        UsersContactInformation usersContactInformation =
                new UsersContactInformation(0L, 2L, "Ivan", "Ivanov", 18, 89097796609L, "abc12334@mail.ru",LocalDate.now(), "CAT");
        Report report =
                new Report(0L, 2L,
                        "food good", "feel well", "behavior perfect", LocalDate.now());


        var savedUser = userContactInformationRepository.save(usersContactInformation);
        report.setUsersContactInformation(savedUser);
        reportRepository.save(report);

        ResponseEntity<List<Report>> exchange =
                restTemplate.exchange(url + port + "/user/" + usersContactInformation.getId(),
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());

    }

    @Test
    void setTrialPeriodForUser__returnUpdatedUserInformation() {
        UsersContactInformation usersContactInformation =
                new UsersContactInformation(0L, 2L, "Serg", "Ivanov", 18, 89234090909L, "abw123@mail.ru",LocalDate.now(), "CAT");

        userContactInformationRepository.save(usersContactInformation);

        usersContactInformation.setTrialPeriod(LocalDate.now().plusDays(30));

        var savedUser = userContactInformationRepository.save(usersContactInformation);
        ResponseEntity<UsersContactInformation> response =
                restTemplate.exchange(url + port + "/user/trail/" + usersContactInformation.getId(),
                        HttpMethod.PUT,
                        new HttpEntity<UsersContactInformation>(savedUser),
                        UsersContactInformation.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void extendTrialPeriod__returnUpdatedInformation() {
        UsersContactInformation usersContactInformation =
                new UsersContactInformation(0L, 2L, "Merg", "Ivanov", 18, 890956769L, "abc23@mail.ru", LocalDate.now(),"CAT");

        userContactInformationRepository.save(usersContactInformation);

        usersContactInformation.setTrialPeriod(LocalDate.now().plusDays(30));

        userContactInformationRepository.save(usersContactInformation);

        usersContactInformation.setTrialPeriod(usersContactInformation.getTrialPeriod().plusDays(14));

        userContactInformationRepository.save(usersContactInformation);

        ResponseEntity<UsersContactInformation> response =
                restTemplate.exchange(url + port + "/user/extend/" + usersContactInformation.getId(),
                        HttpMethod.PUT,
                        new HttpEntity<UsersContactInformation>(usersContactInformation),
                        UsersContactInformation.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
