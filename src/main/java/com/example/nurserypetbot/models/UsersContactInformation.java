package com.example.nurserypetbot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users_contact_information")
@Entity
public class UsersContactInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @Column(name = "trial_period")
    private LocalDate trialPeriod;

    @Column(name = "pet_shelter_type")
    private String petShelterType;

    @OneToMany(mappedBy = "usersContactInformation")
    @JsonIgnore
    private List<Cat> cats;

    @OneToMany(mappedBy = "usersContactInformation")
    @JsonIgnore
    private List<Dog> dogs;

    @OneToMany(mappedBy = "usersContactInformation")
    @JsonIgnore
    private List<Report> reports;

    public UsersContactInformation(long chatId, String name, String surname, int age, long phoneNumber, String email, LocalDate trialPeriod, String petShelterType) {
        this.chatId = chatId;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.trialPeriod = trialPeriod;
        this.petShelterType = petShelterType;
    }
    public UsersContactInformation(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getAge() {
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

    public LocalDate getTrialPeriod() {
        return trialPeriod;
    }

    public void setTrialPeriod(LocalDate trialPeriod) {
        this.trialPeriod = trialPeriod;
    }

    public String getPetShelterType() {
        return petShelterType;
    }

    public void setPetShelterType(String petShelterType) {
        this.petShelterType = petShelterType;
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

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}


