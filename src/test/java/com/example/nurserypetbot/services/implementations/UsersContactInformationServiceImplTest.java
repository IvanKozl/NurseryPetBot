package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.repository.CatUsersContactInformationRepository;
import com.example.nurserypetbot.repository.DogUsersContactInformationRepository;
import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UsersContactInformationServiceImplTest {
    @Mock
    CatUsersContactInformationRepository catRepository;
    DogUsersContactInformationRepository dogRepository;

    @InjectMocks
    UsersContactInformationServiceImpl underTest;
    TelegramBot telegramBot;




    @Test
    void addNewUsersInformation__informationAddedInDB(){

    }

}
