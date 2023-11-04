package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PhotoRepository extends JpaRepository<Photo, Long> {
//    Optional<Photo> findByReport_id(long report_id);
}
