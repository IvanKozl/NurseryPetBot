package com.example.nurserypetbot.services.services;

import com.example.nurserypetbot.models.Cat;

public interface CatService {
    Cat create(Cat cat);

    Cat read(long id);

    Cat update(Cat cat);

    Cat delete(long id);

}
