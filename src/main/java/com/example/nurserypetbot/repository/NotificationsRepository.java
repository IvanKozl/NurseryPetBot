package com.example.nurserypetbot.repository;


import com.example.nurserypetbot.models.Notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface NotificationsRepository extends JpaRepository<Notification, Long> {
}
