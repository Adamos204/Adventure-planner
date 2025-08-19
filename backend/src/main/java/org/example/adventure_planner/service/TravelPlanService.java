package org.example.adventure_planner.service;

import org.example.adventure_planner.dto.TravelPlanRequestDTO;
import org.example.adventure_planner.dto.TravelPlanResponseDTO;

import java.util.List;

public interface TravelPlanService {
    List<TravelPlanResponseDTO> getAllTravelPlans();
    TravelPlanResponseDTO getTravelPlanById(Long id);
    TravelPlanResponseDTO addTravelPlan(TravelPlanRequestDTO dto);
    TravelPlanResponseDTO updateTravelPlan(Long id, TravelPlanRequestDTO dto);
    void deleteTravelPlan(Long id);
    List<TravelPlanResponseDTO> getTravelPlansByUserAdventureId(Long userAdventureId);
}
