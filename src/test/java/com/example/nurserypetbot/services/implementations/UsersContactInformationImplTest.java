package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.enums.PetShelter;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.repository.CatUsersContactInformationRepository;
import com.example.nurserypetbot.repository.DogUsersContactInformationRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UsersContactInformationImplTest {
    @Mock
    CatUsersContactInformationRepository catRepository;
    DogUsersContactInformationRepository dogRepository;

    @InjectMocks
    UsersContactInformationImpl underTest;
    TelegramBot telegramBot;




    @Test
    void addNewUsersInformation__informationAddedInDB(){

    }

}