package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.exceptions.CatException;
import com.example.nurserypetbot.models.Cat;
import com.example.nurserypetbot.repository.CatRepository;
import com.example.nurserypetbot.services.services.CatService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CatServiceImpl implements CatService {
    private final CatRepository catRepository;

    public CatServiceImpl(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    @Override
    public Cat create(Cat cat) {
        if(catRepository.findByNameAndAge(cat.getName(), cat.getAge()).isPresent()){
            throw new CatException("We have already got this cat in our DB, please," +
                    "check the information");
        }
        Cat savedCat = catRepository.save(cat);
        return savedCat;
    }

    @Override
    public Cat read(long id) {
        Optional<Cat> cat = catRepository.findById(id);

        if (cat.isEmpty()) {
            throw new CatException("Cat not found");
        }
        return cat.get();
    }

    @Override
    public Cat update(Cat cat) {
        if (catRepository.findById(cat.getId()).isEmpty()) {
            throw new CatException("Cat not found");
        }
        Cat updatedCat = catRepository.save(cat);
        return updatedCat;
    }

    @Override
    public Cat delete(long id) {
        Optional<Cat> cat = catRepository.findById(id);
        if (cat.isEmpty()) {
            throw new CatException("Cat not found");
        }
        catRepository.deleteById(id);
        return cat.get();
    }
}
