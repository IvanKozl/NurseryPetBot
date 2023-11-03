package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
 Optional<Report> findReportForEachUserByDay(long userId, LocalDateTime dateTime);

}
