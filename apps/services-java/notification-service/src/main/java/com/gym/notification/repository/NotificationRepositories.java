package com.gym.notification.repository;

import com.gym.notification.model.NotificationLog;
import com.gym.notification.model.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    Optional<NotificationTemplate> findByName(String name);
}

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
}
