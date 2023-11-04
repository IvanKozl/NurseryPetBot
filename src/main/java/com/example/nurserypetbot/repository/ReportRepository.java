package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByDateTime(LocalDate date);
    Optional<Report> findByUsersContactInformation(int number);
    Optional<Report> findByChatId(int number);

    List<Report> findAll();
}
