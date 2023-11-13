package com.example.nurserypetbot.controller;

import com.example.nurserypetbot.controllers.CatController;
import com.example.nurserypetbot.models.Cat;
import com.example.nurserypetbot.repository.CatRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(controllers = {CatController.class})
public class CatControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CatRepository catRepository;

    Cat cat = new Cat(54L, 54L,"cat", "Mark",
            "male", 6, "yes","no", "don't like dogs" );
    @Test
    void create__returnStatus200AndSavedToDb() throws Exception {
        when(catRepository.save(cat)).thenReturn(cat);

        mockMvc.perform(post("/cat")
                        .content(objectMapper.writeValueAsString(cat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
    @Test
    void read__returnStatus200() throws Exception {
        catRepository.save(cat);
        when(catRepository.findById(cat.getId()))
                .thenReturn(Optional.of(cat));

        mockMvc.perform(get("/cat/" + cat.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void update__returnStatus200() throws Exception{
        when(catRepository.findById(cat.getId()))
                .thenReturn(Optional.of(cat));
        when(catRepository.save(cat))
                .thenReturn(cat);

        mockMvc.perform(put("/cat")
                        .content(objectMapper.writeValueAsString(cat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        ;
    }
    @Test
    void delete__returnStatus200AndDeletedInformation() throws Exception {
        when(catRepository.findById(cat.getId()))
                .thenReturn(Optional.of(cat));

        mockMvc.perform(delete("/cat/" + cat.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

