package com.example.nurserypetbot.models;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name = "cat")
public class Cat extends Pet {

    public Cat(long chatId, String type, String name, String gender,
               int age, String sterile, String feature, String featureAdd) {
        super(chatId, type, name, gender, age, sterile, feature, featureAdd);
    }
//    public Cat(long id, long chatId, String type, String name, String gender,
//               int age, String sterile, String feature, String featureAdd) {
//        super(id, chatId, type, name, gender, age, sterile, feature, featureAdd);
//    }

}
