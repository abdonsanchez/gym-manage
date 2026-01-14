package com.gym.user.repository;

import com.gym.user.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByDocumentId(String documentId);

    boolean existsByEmail(String email);

    boolean existsByDocumentId(String documentId);
}
