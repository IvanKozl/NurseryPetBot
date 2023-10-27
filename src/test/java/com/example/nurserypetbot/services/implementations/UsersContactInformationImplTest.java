package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.parser.Parser;
import com.example.nurserypetbot.repository.CatUsersContactInformationRepository;
import com.example.nurserypetbot.repository.DogUsersContactInformationRepository;
import com.example.nurserypetbot.services.services.UsersContactInformationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersContactInformationImplTest {
    @Mock
    CatUsersContactInformationRepository catRepository;
    DogUsersContactInformationRepository dogRepository;

    @InjectMocks
    UsersContactInformationImpl underTest;
    TelegramBot telegramBot;


    UsersContactInformation usersContactInformation =
            new UsersContactInformation
                    (1, 12, "Ivan", "Ivanov", 20,
                            89099094545L, "abc123@mail.ru", "cat" );
    String string = "cat Ivan Ivanov 20 89099094545 abc123@mail.ru";

    public Message message(String string){
    string = "cat Ivan Ivanov 20 89099094545 abc123@mail.ru";
   return message(string);
    }
    SendMessage result = new SendMessage(12L, String.format("OK, your information successfully added"));


    @Test
    void addNewUsersInformation__informationAddedInDB(){

    }

}
