package com.example.nurserypetbot.controllers;

import com.example.nurserypetbot.models.Dog;
import com.example.nurserypetbot.services.interfaces.DogService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/dog")
public class DogController {
    public final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }
    @PostMapping
    public Dog create(@RequestBody Dog dog) {
        return dogService.create(dog);
    }

    @GetMapping(path = "{id}")
    public Dog read(@PathVariable long id) {
        return dogService.read(id);
    }

    @PutMapping
    public Dog update(@RequestBody Dog dog) {
        return dogService.update(dog);
    }

    @DeleteMapping(path = "{id}")
    public Dog delete(@PathVariable long id) {
        return dogService.delete(id);
    }


}
