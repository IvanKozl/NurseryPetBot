package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.exceptions.DogException;
import com.example.nurserypetbot.models.Dog;
import com.example.nurserypetbot.repository.DogRepository;
import com.example.nurserypetbot.services.interfaces.DogService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DogServiceImpl implements DogService {
    private final DogRepository dogRepository;

    public DogServiceImpl(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    @Override
    public Dog create(Dog dog) {
        if (dogRepository.findByNameAndAge(dog.getName(), dog.getAge()).isPresent()) {
            throw new DogException("We have already got this dog in our DB, please," +
                    "check the information");
        }
        return dogRepository.save(dog);
    }

    @Override
    public Dog read(long id) {
        Optional<Dog> dog = dogRepository.findByChatId(id);

        if (dog.isEmpty()) {
            throw new DogException("Dog not found");
        }
        return dog.get();
    }

    @Override
    public Dog update(Dog dog) {
        if (dogRepository.findById(dog.getId()).isEmpty()) {
            throw new DogException("Dog not found");
        }
        return dogRepository.save(dog);
    }

    @Override
    public Dog delete(long id) {
        Optional<Dog> dog = dogRepository.findById(id);
        if (dog.isEmpty()) {
            throw new DogException("Dog not found");
        }
        dogRepository.deleteById(id);
        return dog.get();
    }
}
