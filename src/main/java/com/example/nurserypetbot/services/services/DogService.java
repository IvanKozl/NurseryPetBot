package com.example.nurserypetbot.services.services;

import com.example.nurserypetbot.models.Cat;
import com.example.nurserypetbot.models.Dog;

public interface DogService {
    Dog create(Dog dog);

    Dog read(long id);

    Dog update(Dog dog);

    Dog delete(long id);
}
