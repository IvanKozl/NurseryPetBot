package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.UsersContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DogUsersContactInformationRepository
        extends JpaRepository<UsersContactInformation, Long> {
}
