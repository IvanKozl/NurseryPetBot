package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.UsersContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatUsersContactInformationRepository
        extends JpaRepository<UsersContactInformation, Long> {
}
