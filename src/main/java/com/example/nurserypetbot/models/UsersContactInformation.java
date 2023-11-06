package com.example.nurserypetbot.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
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

    public UsersContactInformation(Long id, long chatId, String name, String surname, int age, long phoneNumber, String email, LocalDate trialPeriod, String petShelterType) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.trialPeriod = trialPeriod;
        this.petShelterType = petShelterType;
    }
}


