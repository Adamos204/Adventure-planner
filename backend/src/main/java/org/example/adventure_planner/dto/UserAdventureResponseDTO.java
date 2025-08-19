package org.example.adventure_planner.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserAdventureResponseDTO {
    private Long id;
    private String name;
    private String location;
    private String description;
    private LocalDate date;
    private String notes;
    private Integer lengthInDays;
    private Double lengthInKm;
    private String startLocation;
    private String endLocation;
    private Long userId;
}
