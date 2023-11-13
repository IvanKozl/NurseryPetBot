package com.example.nurserypetbot.controllers;

import com.example.nurserypetbot.models.Cat;
import com.example.nurserypetbot.repository.CatRepository;
import com.example.nurserypetbot.repository.UsersContactInformationRepository;
import com.example.nurserypetbot.services.interfaces.CatService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/cat")
public class CatController {
    public final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @PostMapping
    public Cat create(@RequestBody Cat cat) {
        return catService.create(cat);
    }

    @GetMapping(path = "{id}")
    public Cat read(@PathVariable long id) {

        return catService.read(id);
    }

    @PutMapping
    public Cat update(@RequestBody Cat cat) {

        return catService.update(cat);
    }

    @DeleteMapping(path = "{id}")
    public Cat delete(@PathVariable long id) {

        return catService.delete(id);
    }

}
