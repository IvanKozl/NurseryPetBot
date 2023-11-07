package com.example.nurserypetbot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    public Report(){

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

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getFeel() {
        return feel;
    }

    public void setFeel(String feel) {
        this.feel = feel;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isFotoCheck() {
        return fotoCheck;
    }

    public void setFotoCheck(boolean fotoCheck) {
        this.fotoCheck = fotoCheck;
    }

    public boolean isReportCheck() {
        return reportCheck;
    }

    public void setReportCheck(boolean reportCheck) {
        this.reportCheck = reportCheck;
    }

    public UsersContactInformation getUsersContactInformation() {
        return usersContactInformation;
    }

    public void setUsersContactInformation(UsersContactInformation usersContactInformation) {
        this.usersContactInformation = usersContactInformation;
    }
}
