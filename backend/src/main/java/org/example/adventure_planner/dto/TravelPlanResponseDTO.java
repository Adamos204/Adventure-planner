package org.example.adventure_planner.dto;

import lombok.Data;
import java.sql.Time;

@Data
public class TravelPlanResponseDTO {
    private Long id;
    private String type;
    private String departureLocation;
    private String arrivalLocation;
    private Time departureTime;
    private Time arrivalTime;
    private String notes;
    private Long userAdventureId;
}
