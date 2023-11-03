package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.Pet;
import com.example.nurserypetbot.models.UsersContactInformation;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CatUsersContactInformationRepository
        extends JpaRepository<UsersContactInformation, Long> {
   Optional<UsersContactInformation>findByChat_id(Long chat_id);
   @NotNull
   Optional<UsersContactInformation>findById(@NotNull Long id);
   Optional<UsersContactInformation>findByPhone(Long phoneNumber);
}
