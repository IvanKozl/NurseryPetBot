package com.example.nurserypetbot.controller;

import com.example.nurserypetbot.controllers.DogController;
import com.example.nurserypetbot.models.Dog;
import com.example.nurserypetbot.repository.DogRepository;
import com.example.nurserypetbot.services.implementations.DogServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {DogController.class})
public class DogControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    DogController dogController;
    @SpyBean
    DogServiceImpl dogService;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DogRepository dogRepository;

    Dog dog = new Dog(1L, 1L, "dog", "Bob",
            "male", 4, "yes", "no", "don't like parrots");

    @Test
    void create__returnStatus200AndSavedToDb() throws Exception {
        when(dogRepository.save(dog)).thenReturn(dog);
        mockMvc.perform(post("/dog")
                        .content(objectMapper.writeValueAsString(dog))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void read__returnStatus200() throws Exception {
        dogRepository.save(dog);
        when(dogRepository.findById(dog.getId()))
                .thenReturn(Optional.of(dog));

        mockMvc.perform(get("/dog/" + dog.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void update__returnStatus200() throws Exception {
        when(dogRepository.findById(dog.getId()))
                .thenReturn(Optional.of(dog));
        when(dogRepository.save(dog))
                .thenReturn(dog);

        mockMvc.perform(put("/dog")
                        .content(objectMapper.writeValueAsString(dog))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }

    @Test
    void delete__returnStatus200AndDeletedInformation() throws Exception {
        when(dogRepository.findById(dog.getId()))
                .thenReturn(Optional.of(dog));

        mockMvc.perform(delete("/dog/" + dog.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

