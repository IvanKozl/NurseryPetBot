package com.example.nurserypetbot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "food")
    private String food;
    @Column(name = "feel")
    private String feel;
    @Column(name = "behavior")
    private String behavior;
    @Column(name = "date_time")
    private LocalDate dateTime;
    @Column(name = "foto_check")
    private boolean fotoCheck;
    @Column(name = "report_check")
    private boolean reportCheck;
    @ManyToOne
    @JoinColumn(name = "user_number")
    @JsonIgnore
    private UsersContactInformation usersContactInformation;

    public Report(long chatId, String food, String feel, String behavior, LocalDate dateTime) {
        this.chatId = chatId;
        this.food = food;
        this.feel = feel;
        this.behavior = behavior;
        this.dateTime = dateTime;
    }


}
