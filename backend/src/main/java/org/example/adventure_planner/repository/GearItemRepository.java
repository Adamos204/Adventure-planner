package org.example.adventure_planner.repository;

import org.example.adventure_planner.model.GearItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GearItemRepository extends JpaRepository<GearItem, Long> {
    List<GearItem> findByAdventure_Id(Long id);
}
