package com.example.nurserypetbot.controllers;

import com.example.nurserypetbot.models.Cat;
import com.example.nurserypetbot.models.Pet;
import com.example.nurserypetbot.models.UsersContactInformation;
import com.example.nurserypetbot.services.services.CatService;
import org.springframework.web.bind.annotation.*;
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
