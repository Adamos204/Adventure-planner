package org.example.adventure_planner.service;

import org.example.adventure_planner.dto.GearItemRequestDTO;
import org.example.adventure_planner.dto.GearItemResponseDTO;

import java.util.List;

public interface GearItemService {
    List<GearItemResponseDTO> getAllGearItems();
    GearItemResponseDTO getGearItemById(Long id);
    GearItemResponseDTO addGearItem(GearItemRequestDTO requestDto);
    GearItemResponseDTO updateGearItem(Long id, GearItemRequestDTO requestDto);
    void deleteGearItem(Long id);
    List<GearItemResponseDTO> getAllAdventureItems(Long adventureId);
}
