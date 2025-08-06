package org.example.adventure_planner.service;

import org.example.adventure_planner.model.GearItem;

import java.util.List;
import java.util.Optional;

public interface GearItemService {
    List<GearItem> getAllGearItems();
    Optional<GearItem> getGearItemById(Long id);
    GearItem addGearItem(GearItem gearItem);
    GearItem updateGearItem(GearItem gearItem);
    void deleteGearItem(Long id);
    List<GearItem> getAllAdventureItems(Long adventureId);
}
