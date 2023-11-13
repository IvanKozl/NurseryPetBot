package com.example.nurserypetbot.services.implementations;

import com.example.nurserypetbot.models.Dog;
import com.example.nurserypetbot.repository.DogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DogServiceImplTest {

    @InjectMocks
    private DogServiceImpl dogService;

    @Mock
    private DogRepository dogRepository;

    Dog dog = new Dog(1L, 7L,"dog", "Doggy", "M",2, "sterile", "yes", "god");

    @Test
    public void createDog_whenDogNotExists_expectSavedDog() {
        when(dogRepository.findByNameAndAge(dog.getName(), dog.getAge())).thenReturn(Optional.empty());
        when(dogRepository.save(dog)).thenReturn(dog);
        Dog savedDog = dogService.create(dog);
        assertNotNull(savedDog);
        assertEquals(dog.getName(), savedDog.getName());
        assertEquals(dog.getAge(), savedDog.getAge());
        verify(dogRepository, times(1)).findByNameAndAge(dog.getName(), dog.getAge());
        verify(dogRepository, times(1)).save(dog);
    }

    @Test
    public void readDog_whenDogExists_expectFoundDog() {
        long dogId = 1;
        when(dogRepository.findById(dogId)).thenReturn(Optional.of(dog));
        Dog foundDog = dogService.read(dogId);
        assertNotNull(foundDog);
        assertEquals(dog.getName(), foundDog.getName());
        assertEquals(dog.getAge(), foundDog.getAge());
        verify(dogRepository, times(1)).findById(dogId);
    }

    @Test
    public void updateCat_whenCatExists_expectUpdatedCat() {
        when(dogRepository.findById(dog.getId())).thenReturn(Optional.of(dog));
        when(dogRepository.save(dog)).thenReturn(dog);
        Dog updatedDog = dogService.update(dog);
        assertNotNull(updatedDog);
        assertEquals(dog.getName(), updatedDog.getName());
        assertEquals(dog.getAge(), updatedDog.getAge());
        verify(dogRepository, times(1)).findById(dog.getId());
        verify(dogRepository, times(1)).save(dog);
    }

    @Test
    public void deleteDog_whenDogExists_expectDeletedDog() {
        long dogId = 1;
        when(dogRepository.findById(dogId)).thenReturn(Optional.of(dog));
        Dog deletedDog = dogService.delete(dogId);
        assertNotNull(deletedDog);
        assertEquals(dog.getName(), deletedDog.getName());
        assertEquals(dog.getAge(), deletedDog.getAge());
        verify(dogRepository, times(1)).findById(dogId);
        verify(dogRepository, times(1)).deleteById(dogId);
    }

}