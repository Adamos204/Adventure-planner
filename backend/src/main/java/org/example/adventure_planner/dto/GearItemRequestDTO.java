package org.example.adventure_planner.dto;

import lombok.Data;

@Data
public class GearItemRequestDTO {
    private String name;
    private int quantity;
    private Long adventureId;
}