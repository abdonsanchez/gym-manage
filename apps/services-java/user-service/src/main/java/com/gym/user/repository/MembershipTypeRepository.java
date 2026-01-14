package com.gym.user.repository;

import com.gym.user.model.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipTypeRepository extends JpaRepository<MembershipType, Integer> {
    Optional<Member> findByName(String name);
}
