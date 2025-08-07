package org.example.adventure_planner.service;

import org.example.adventure_planner.model.TravelPlan;

import java.util.List;
import java.util.Optional;

public interface TravelPlanService {
    List<TravelPlan> getAllTravelPlans();
    Optional<TravelPlan> getTravelPlanById(Long id);
    TravelPlan addTravelPlan(TravelPlan travelPlan);
    TravelPlan updateTravelPlan(TravelPlan travelPlan);
    void deleteTravelPlan(Long id);
    List<TravelPlan> getTravelPlansByUserAdventureId(Long userAdventureId);
}

