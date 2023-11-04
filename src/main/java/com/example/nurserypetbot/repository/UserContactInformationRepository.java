package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.UsersContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserContactInformationRepository extends JpaRepository<UsersContactInformation, Long> {

}
