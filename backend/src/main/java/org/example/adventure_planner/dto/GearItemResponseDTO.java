package org.example.adventure_planner.dto;

import lombok.Data;

@Data
public class GearItemResponseDTO {
    private Long id;
    private String name;
    private int quantity;
    private boolean packed;
    private Long adventureId;
}