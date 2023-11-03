package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
