package com.example.nurserypetbot.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "food")
    private String food;

    @Column(name = "feel")
    private String feel;
    @Column(name = "behavior")
    private String behavior;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UsersContactInformation usersContactInformation;

    public Report(long id, long chatId, String food, String feel, String behavior,
                  LocalDateTime dateTime) {
        this.id = id;
        this.chatId = chatId;
        this.food = food;
        this.feel = feel;
        this.behavior = behavior;
        this.dateTime = dateTime;
    }
    public Report(){

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
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Report report = (Report) o;
        return chatId == report.chatId
                && Objects.equals(food, report.food)
                && Objects.equals(feel, report.feel)
                && Objects.equals(behavior, report.behavior)
                && Objects.equals(dateTime, report.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, food, feel, behavior, dateTime);
    }


}
