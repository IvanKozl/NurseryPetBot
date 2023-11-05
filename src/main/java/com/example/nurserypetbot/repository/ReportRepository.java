package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByUsersContactInformationAndDateTime(long userId, LocalDateTime localDateTime);

    List<Report> findAllByUsersContactInformationId(long userId);

}
