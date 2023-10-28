package com.example.nurserypetbot.repository;

import com.example.nurserypetbot.models.Dog;
import com.example.nurserypetbot.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DogRepository extends JpaRepository<Dog, Long> {
    Optional<Dog> findByNameAndAge(String name, int age);
}
