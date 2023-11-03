package com.example.nurserypetbot.models;

import com.example.nurserypetbot.enums.PetShelter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class UsersContactInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private int age;

    @Column(name = "phone")
    private long phoneNumber;

    @Column(name = "email")
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "pet_shelter_type")
    private PetShelter petShelterType;
    @OneToMany(mappedBy = "usersContactInformation")
    @JsonIgnore
    private List<Cat> cats;
    @OneToMany(mappedBy = "usersContactInformation")
    @JsonIgnore
    private List<Dog> dogs;
    @OneToMany(mappedBy = "usersContactInformation")
    @JsonIgnore
    private List<Report> reports;

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public UsersContactInformation() {
    }

    public UsersContactInformation(long id, long chatId, String name, String surname,
                                   int age, long phoneNumber, String email,
                                   PetShelter petShelterType) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.petShelterType = petShelterType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PetShelter getPetType() {
        return petShelterType;
    }

    public void setPetShelterType(PetShelter petShelterType) {
        this.petShelterType = petShelterType;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UsersContactInformation usersContactInformation = (UsersContactInformation) o;
        return age == usersContactInformation.age && phoneNumber == usersContactInformation.phoneNumber
                && chatId == usersContactInformation.chatId
                && Objects.equals(name, usersContactInformation.name)
                && Objects.equals(surname, usersContactInformation.surname)
                && Objects.equals(email, usersContactInformation.email)
                && Objects.equals(petShelterType, usersContactInformation.petShelterType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, name, surname, age, phoneNumber, email, petShelterType);
    }

    public PetShelter getPetShelterType() {
        return petShelterType;
    }

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }
}


