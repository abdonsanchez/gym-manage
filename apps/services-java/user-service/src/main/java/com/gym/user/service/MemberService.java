package com.gym.user.service;

import com.gym.user.dto.MemberDTO;
import com.gym.user.mapper.MemberMapper;
import com.gym.user.model.MedicalHistory;
import com.gym.user.model.Member;
import com.gym.user.model.MembershipType;
import com.gym.user.repository.MemberRepository;
import com.gym.user.repository.MembershipTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MembershipTypeRepository membershipTypeRepository;
    private final MemberMapper memberMapper;

    @Transactional(readOnly = true)
    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(memberMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDTO getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(memberMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));
    }

    @Transactional
    public MemberDTO createMember(MemberDTO memberDTO) {
        if (memberRepository.existsByEmail(memberDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (memberRepository.existsByDocumentId(memberDTO.getDocumentId())) {
            throw new IllegalArgumentException("Document ID already exists");
        }

        Member member = memberMapper.toEntity(memberDTO);

        MembershipType membershipType = membershipTypeRepository.findById(memberDTO.getMembershipTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Membership Type not found"));
        member.setMembershipType(membershipType);

        // Set default dates if not provided
        if (member.getMembershipStartDate() == null) {
            member.setMembershipStartDate(LocalDate.now());
        }
        if (member.getMembershipEndDate() == null) {
            // Default logic: 1 month or 1 year depending on type?
            // For simplicity, let's say 1 month for now, logic can be enhanced
            member.setMembershipEndDate(member.getMembershipStartDate().plusMonths(1));
        }

        // Handle Medical History
        if (memberDTO.getMedicalHistory() != null) {
            MedicalHistory history = memberMapper.toEntity(memberDTO.getMedicalHistory());
            history.setMember(member);
            member.setMedicalHistory(history);
        }

        // Handle Emergency Contacts
        if (memberDTO.getEmergencyContacts() != null) {
            member.setEmergencyContacts(memberDTO.getEmergencyContacts().stream()
                    .map(dto -> {
                        var contact = memberMapper.toEntity(dto);
                        contact.setMember(member);
                        return contact;
                    })
                    .collect(Collectors.toList()));
        }

        Member savedMember = memberRepository.save(member);
        return memberMapper.toDTO(savedMember);
    }

    @Transactional
    public MemberDTO updateMember(Long id, MemberDTO memberDTO) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        memberMapper.updateEntityFromDTO(memberDTO, member);

        if (memberDTO.getMembershipTypeId() != null) {
            MembershipType membershipType = membershipTypeRepository.findById(memberDTO.getMembershipTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("Membership Type not found"));
            member.setMembershipType(membershipType);
        }

        return memberMapper.toDTO(memberRepository.save(member));
    }

    @Transactional
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new EntityNotFoundException("Member not found");
        }
        memberRepository.deleteById(id);
    }
}
