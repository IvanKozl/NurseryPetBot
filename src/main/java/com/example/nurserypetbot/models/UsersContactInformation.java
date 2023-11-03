package com.example.nurserypetbot.models;
import com.example.nurserypetbot.enums.PetShelter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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
}


