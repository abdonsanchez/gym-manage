package com.gym.access.repository;

import com.gym.access.model.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {
    List<AccessLog> findByMemberIdOrderByTimestampDesc(Long memberId);
}
