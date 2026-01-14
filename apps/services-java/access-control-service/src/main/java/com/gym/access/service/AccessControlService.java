package com.gym.access.service;

import com.gym.access.model.AccessLog;
import com.gym.access.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccessControlService {

    private final AccessLogRepository accessLogRepository;

    @Transactional
    public AccessLog registerAccess(Long memberId, AccessLog.AccessMethod method, AccessLog.AccessType type,
            String location) {
        // Here we would strictly validate with User Service if member is active.
        // For now, we assume member is active for simplicity or verified by gateway.

        AccessLog log = AccessLog.builder()
                .memberId(memberId)
                .accessMethod(method)
                .accessType(type)
                .location(location)
                .timestamp(LocalDateTime.now())
                .status(AccessLog.AccessStatus.success)
                .build();

        return accessLogRepository.save(log);
    }

    @Transactional(readOnly = true)
    public List<AccessLog> getLogsByMember(Long memberId) {
        return accessLogRepository.findByMemberIdOrderByTimestampDesc(memberId);
    }
}
