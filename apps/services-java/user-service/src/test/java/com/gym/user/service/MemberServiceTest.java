package com.gym.user.service;

import com.gym.user.dto.MemberDTO;
import com.gym.user.mapper.MemberMapper;
import com.gym.user.model.Member;
import com.gym.user.model.MembershipType;
import com.gym.user.repository.MemberRepository;
import com.gym.user.repository.MembershipTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MembershipTypeRepository membershipTypeRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberService memberService;

    private Member member;
    private MemberDTO memberDTO;
    private MembershipType membershipType;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setId(1L);
        member.setEmail("test@test.com");
        member.setDocumentId("12345678");

        memberDTO = new MemberDTO();
        memberDTO.setId(1L);
        memberDTO.setEmail("test@test.com");
        memberDTO.setDocumentId("12345678");
        memberDTO.setMembershipTypeId(1L);

        membershipType = new MembershipType();
        membershipType.setId(1L);
        membershipType.setName("Gold");
    }

    @Test
    void createMember_Success() {
        when(memberRepository.existsByEmail(anyString())).thenReturn(false);
        when(memberRepository.existsByDocumentId(anyString())).thenReturn(false);
        when(membershipTypeRepository.findById(anyLong())).thenReturn(Optional.of(membershipType));
        when(memberMapper.toEntity(any(MemberDTO.class))).thenReturn(member);
        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberMapper.toDTO(any(Member.class))).thenReturn(memberDTO);

        MemberDTO created = memberService.createMember(memberDTO);

        assertNotNull(created);
        assertEquals(memberDTO.getEmail(), created.getEmail());
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void createMember_EmailExists_ThrowsException() {
        when(memberRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> memberService.createMember(memberDTO));
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void getMemberById_Success() {
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberMapper.toDTO(member)).thenReturn(memberDTO);

        MemberDTO found = memberService.getMemberById(1L);

        assertNotNull(found);
        assertEquals(1L, found.getId());
    }

    @Test
    void getMemberById_NotFound_ThrowsException() {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> memberService.getMemberById(1L));
    }
}
