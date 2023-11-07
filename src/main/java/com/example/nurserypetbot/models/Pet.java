package com.example.nurserypetbot.models;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "type")
    private String type;
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private String gender;
    @Column(name = "age")
    private int age;
    @Column(name = "sterile")
    private String sterile;
    @Column(name = "feature")
    private String feature;
    @Column(name = "feature_add")
    private String featureAdd;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersContactInformation usersContactInformation;

    public Pet(long chatId, String type, String name, String gender, int age, String sterile, String feature, String featureAdd) {

        this.chatId = chatId;
        this.type = type;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.sterile = sterile;
        this.feature = feature;
        this.featureAdd = featureAdd;
    }


//
//    public Pet(long id, long chatId, String type, String name, String gender,
//                int age, String sterile, String feature, String featureAdd) {
//        this.id = id;
//        this.chatId = chatId;
//        this.type = type;
//        this.name = name;
//        this.gender = gender;
//        this.age = age;
//        this.sterile = sterile;
//        this.feature = feature;
//        this.featureAdd = featureAdd;
//    }
//    public Pet(){
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//    public long getChatId(){
//        return chatId;
//    }
//    public void setChatId(long chatId){
//        this.chatId = chatId;
//    }
//
//    public String getType() {
//        return type;
//    }
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public String getSterile() {
//        return sterile;
//    }
//
//    public void setSterile(String sterile) {
//        this.sterile = sterile;
//    }
//
//    public String getFeature() {
//        return feature;
//    }
//
//    public void setFeature(String feature) {
//        this.feature = feature;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        Pet pet = (Pet) o;
//        return age == pet.age
//                && chatId == pet.chatId
//                && Objects.equals(type, pet.type)
//                && Objects.equals(name, pet.name)
//                && Objects.equals(gender, pet.gender)
//                && Objects.equals(sterile, pet.sterile)
//                && Objects.equals(feature, pet.feature)
//                && Objects.equals(featureAdd, pet.featureAdd);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(chatId, type, name, gender, age, sterile, feature, featureAdd);
//    }
}

