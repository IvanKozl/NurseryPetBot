package com.example.nurserypetbot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition
public class NurseryPetBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(NurseryPetBotApplication.class, args);
    }
}
