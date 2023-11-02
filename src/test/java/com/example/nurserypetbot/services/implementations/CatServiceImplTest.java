package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.exceptions.CatException;
import com.example.nurserypetbot.models.Cat;
import com.example.nurserypetbot.repository.CatRepository;
import com.example.nurserypetbot.services.services.CatService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CatServiceImplTest {

    @InjectMocks
    private CatServiceImpl catService;

    @Mock
    private CatException catException;

    @Mock
    private CatRepository catRepository;

    Cat cat = new Cat(1,2,"kiitti","kat","rft",2,"sterile","yes","god");
    @Test
    public void createCat_whenCatNotExists_expectSavedCat() {
        when(catRepository.findByNameAndAge(cat.getName(), cat.getAge())).thenReturn(Optional.empty());
        when(catRepository.save(cat)).thenReturn(cat);
        Cat savedCat = catService.create(cat);
        assertNotNull(savedCat);
        assertEquals(cat.getName(), savedCat.getName());
        assertEquals(cat.getAge(), savedCat.getAge());
        verify(catRepository, Mockito.times(1)).findByNameAndAge(cat.getName(), cat.getAge());
        verify(catRepository, Mockito.times(1)).save(cat);
    }

    @Test
    public void readCat_whenCatExists_expectFoundCat() {
        long catId = 1;
        when(catRepository.findById(catId)).thenReturn(Optional.of(cat));
        Cat foundCat = catService.read(catId);
        assertNotNull(foundCat);
        assertEquals(cat.getName(), foundCat.getName());
        assertEquals(cat.getAge(), foundCat.getAge());
        verify(catRepository, Mockito.times(1)).findById(catId);
    }

    @Test
    public void updateCat_whenCatExists_expectUpdatedCat() {
        when(catRepository.findById(cat.getId())).thenReturn(Optional.of(cat));
        when(catRepository.save(cat)).thenReturn(cat);
        Cat updatedCat = catService.update(cat);
        assertNotNull(updatedCat);
        assertEquals(cat.getName(), updatedCat.getName());
        assertEquals(cat.getAge(), updatedCat.getAge());
        verify(catRepository, Mockito.times(1)).findById(cat.getId());
        verify(catRepository, Mockito.times(1)).save(cat);
    }

    @Test
    public void deleteCat_whenCatExists_expectDeletedCat() {
        long catId = 1;
        Mockito.when(catRepository.findById(catId)).thenReturn(Optional.of(cat));
        Cat deletedCat = catService.delete(catId);
        assertNotNull(deletedCat);
        assertEquals(cat.getName(), deletedCat.getName());
        assertEquals(cat.getAge(), deletedCat.getAge());
        verify(catRepository, Mockito.times(1)).findById(catId);
        verify(catRepository, Mockito.times(1)).deleteById(catId);
    }

}