package com.gym.notification.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;

    // Example listener for future payment events
    // @KafkaListener(topics = "payment.success", groupId = "notification-group")
    // public void handlePaymentSuccess(String message) {
    // log.info("Received payment event: {}", message);
    // // Parse JSON and call notificationService.sendNotification(...)
    // }
}
