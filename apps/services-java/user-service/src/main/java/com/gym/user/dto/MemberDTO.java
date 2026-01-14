package com.gym.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MemberDTO {

    private Long id;

    @NotBlank
    @JsonProperty("document_id")
    private String documentId;

    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @NotBlank
    @Email
    private String email;

    private String phone;

    @NotNull
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull
    private String gender;

    private String address;

    @JsonProperty("membership_type_id")
    @NotNull
    private Integer membershipTypeId;

    @JsonProperty("membership_start_date")
    private LocalDate membershipStartDate; // Opcional, por defecto hoy

    @JsonProperty("medical_history")
    private MedicalHistoryDTO medicalHistory;

    @JsonProperty("emergency_contacts")
    private List<EmergencyContactDTO> emergencyContacts;
}
