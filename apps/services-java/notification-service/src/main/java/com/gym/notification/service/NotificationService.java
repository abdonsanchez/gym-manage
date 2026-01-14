package com.gym.notification.service;

import com.gym.notification.model.NotificationLog;
import com.gym.notification.model.NotificationTemplate;
import com.gym.notification.repository.NotificationLogRepository;
import com.gym.notification.repository.NotificationTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationTemplateRepository templateRepository;
    private final NotificationLogRepository logRepository;

    public void sendNotification(String templateName, Long memberId, String recipient, Map<String, String> params) {
        log.info("Preparing to send notification: {} to {}", templateName, recipient);

        NotificationTemplate template = templateRepository.findByName(templateName)
                .orElse(null);

        String content = "Template not found: " + templateName;
        if (template != null) {
            content = replacePlaceholders(template.getBody(), params);
        } else {
            log.warn("Template {} not found, logging failure", templateName);
        }

        // Mock sending
        log.info("Sending [{}] to {}: {}", template != null ? template.getType() : "UNKNOWN", recipient, content);

        NotificationLog notificationLog = NotificationLog.builder()
                .memberId(memberId)
                .templateId(template != null ? template.getId() : null)
                .channel(template != null ? template.getType() : NotificationTemplate.NotificationType.email)
                .recipient(recipient)
                .content(content)
                .status(NotificationLog.Status.sent)
                .build();

        logRepository.save(notificationLog);
    }

    private String replacePlaceholders(String template, Map<String, String> params) {
        String result = template;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return result;
    }
}
