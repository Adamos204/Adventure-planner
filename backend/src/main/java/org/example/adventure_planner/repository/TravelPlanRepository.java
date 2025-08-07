package org.example.adventure_planner.repository;

import org.example.adventure_planner.model.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {
    List<TravelPlan> findByUserAdventureId(Long userAdventureId);
}

