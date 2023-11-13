package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatRepository extends JpaRepository<Cat, Long> {
    Optional<Cat> findByNameAndAge(String name, int age);
}
