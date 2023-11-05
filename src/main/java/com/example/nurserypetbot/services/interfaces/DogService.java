package com.example.nurserypetbot.services.interfaces;

import com.example.nurserypetbot.models.Dog;

public interface DogService {
    Dog create(Dog dog);

    Dog read(long id);

    Dog update(Dog dog);

    Dog delete(long id);
}
