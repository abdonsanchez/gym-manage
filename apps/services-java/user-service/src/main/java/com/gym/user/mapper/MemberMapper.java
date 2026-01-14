package com.gym.user.mapper;

import com.gym.user.dto.EmergencyContactDTO;
import com.gym.user.dto.MedicalHistoryDTO;
import com.gym.user.dto.MemberDTO;
import com.gym.user.model.EmergencyContact;
import com.gym.user.model.MedicalHistory;
import com.gym.user.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "membershipTypeId", source = "membershipType.id")
    MemberDTO toDTO(Member member);

    @Mapping(target = "membershipType", ignore = true) // Set manually in service
    @Mapping(target = "medicalHistory", ignore = true) // Handled separately
    @Mapping(target = "emergencyContacts", ignore = true) // Handled separately
    Member toEntity(MemberDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", ignore = true)
    MedicalHistory toEntity(MedicalHistoryDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", ignore = true)
    EmergencyContact toEntity(EmergencyContactDTO dto);

    // Update existing entity from DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "documentId", ignore = true) // Immutable
    @Mapping(target = "membershipType", ignore = true)
    @Mapping(target = "medicalHistory", ignore = true)
    @Mapping(target = "emergencyContacts", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromDTO(MemberDTO dto, @MappingTarget Member member);

    MedicalHistoryDTO toDTO(MedicalHistory entity);

    EmergencyContactDTO toDTO(EmergencyContact entity);
}
