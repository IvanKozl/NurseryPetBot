package com.example.nurserypetbot.models;

import javax.persistence.*;
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

    public UsersContactInformation(){
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

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        this.surname = surname;
    }

    public long getAge(){
        return age;
    }

    public void setAge(int age){
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


    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }
       UsersContactInformation usersContactInformation = (UsersContactInformation) o;
        return age == usersContactInformation.age && phoneNumber == usersContactInformation.phoneNumber
                && chatId == usersContactInformation.chatId
                && Objects.equals(name, usersContactInformation.name)
                && Objects.equals(surname, usersContactInformation.surname)
                && Objects.equals(email, usersContactInformation.email);
    }
    @Override
    public int hashCode() {
        return Objects.hash(chatId, name, surname, age, phoneNumber, email);
    }



}


