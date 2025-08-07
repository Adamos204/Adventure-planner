package org.example.adventure_planner.service;

import org.example.adventure_planner.model.TravelPlan;
import org.example.adventure_planner.repository.TravelPlanRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelPlanServiceImpl implements TravelPlanService {

    private final TravelPlanRepository travelPlanRepository;
    private final ValidationService validationService;

    public TravelPlanServiceImpl(TravelPlanRepository travelPlanRepository, ValidationService validationService) {
        this.travelPlanRepository = travelPlanRepository;
        this.validationService = validationService;
    }

    @Override
    public List<TravelPlan> getAllTravelPlans() {
        return travelPlanRepository.findAll();
    }

    @Override
    public Optional<TravelPlan> getTravelPlanById(Long id) {
        validationService.requireId(id);
        validationService.requireEntityExists(travelPlanRepository.existsById(id),
                "Travel plan with ID " + id + " not found");
        return travelPlanRepository.findById(id);
    }

    @Override
    public TravelPlan addTravelPlan(TravelPlan travelPlan) {
        validationService.requireNotNull(travelPlan, "Travel plan cannot be null");
        return travelPlanRepository.save(travelPlan);
    }

    @Override
    public TravelPlan updateTravelPlan(TravelPlan travelPlan) {
        validationService.requireNotNull(travelPlan, "Travel plan cannot be null");
        validationService.requireId(travelPlan.getId());
        validationService.requireEntityExists(travelPlanRepository.existsById(travelPlan.getId()),
                "Travel plan not found");
        return travelPlanRepository.save(travelPlan);
    }

    @Override
    public void deleteTravelPlan(Long id) {
        validationService.requireId(id);
        validationService.requireEntityExists(travelPlanRepository.existsById(id),
                "Travel plan not found");
        travelPlanRepository.deleteById(id);
    }

    @Override
    public List<TravelPlan> getTravelPlansByUserAdventureId(Long userAdventureId) {
        validationService.requireId(userAdventureId);
        return travelPlanRepository.findByUserAdventureId(userAdventureId);
    }
}

