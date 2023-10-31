package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
