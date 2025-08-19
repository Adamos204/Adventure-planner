package org.example.adventure_planner.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserTrainingResponseDTO {
    private Long id;
    private LocalDate date;
    private String type;
    private Integer durationInMin;
    private Integer durationInKm;
    private String notes;
    private Long userId;
}