package com.example.nurserypetbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.telegrambot.models.ActualNotification;
import pro.sky.telegrambot.models.Notification;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface NotifictionsRepository extends JpaRepository<Notification, Long> {

    @Query(value = "SELECT tasks.chat_id, tasks.message\n"+ " from notification_task as tasks\n"+" where date = ?1",
            nativeQuery = true)
    List<Ac> getMessageForNowDate(LocalDateTime localDateTime);
}
