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

    public Pet(Long id, long chatId, String type, String name, String gender, int age, String sterile, String feature, String featureAdd) {
        this.id = id;
        this.chatId = chatId;
        this.type = type;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.sterile = sterile;
        this.feature = feature;
        this.featureAdd = featureAdd;
    }
}



