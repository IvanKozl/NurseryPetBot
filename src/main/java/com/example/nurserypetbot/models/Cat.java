package com.example.nurserypetbot.models;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Table(name = "cat")
public class Cat extends Pet {

    public Cat(long id, long chatId, String type, String name, String gender,
               int age, String sterile, String feature, String featureAdd){
       super(chatId,type,name,gender,age,sterile,feature,featureAdd);
    }
//    public Cat(long i, long i1, String kiitti, String kat, String rft, int i2, String sterile, String yes, String god){
//    }
}
