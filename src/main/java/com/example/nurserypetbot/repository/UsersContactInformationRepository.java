package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.UsersContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersContactInformationRepository extends JpaRepository<UsersContactInformation,Long> {
    Optional<UsersContactInformation> findById(long userId);

    List<UsersContactInformation> findAll();
}
