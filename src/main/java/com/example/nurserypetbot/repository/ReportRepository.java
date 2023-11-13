package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByDateTimeAndChatId(LocalDate date, long chatId);

    List<Report> findAll();

    Optional<Report> getByUsersContactInformationId(long id);

    List<Report> findAllByUsersContactInformationId(long userId);
}
