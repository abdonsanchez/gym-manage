package com.gym.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmergencyContactDTO {
    @NotBlank
    private String name;
    
    private String relationship;
    
    @NotBlank
    private String phone;
    
    private String email;
}
