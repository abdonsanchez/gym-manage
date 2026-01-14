package com.gym.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MedicalHistoryDTO {
    @JsonProperty("has_heart_condition")
    private Boolean hasHeartCondition;
    
    @JsonProperty("has_chest_pain")
    private Boolean hasChestPain;
    
    @JsonProperty("has_balance_issues")
    private Boolean hasBalanceIssues;
    
    @JsonProperty("current_medications")
    private String currentMedications;
    
    private String allergies;
    
    @JsonProperty("injuries_history")
    private String injuriesHistory;
    
    @JsonProperty("medical_restrictions")
    private String medicalRestrictions;
}
