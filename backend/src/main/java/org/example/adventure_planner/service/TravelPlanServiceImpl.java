package org.example.adventure_planner.service;

import org.example.adventure_planner.dto.TravelPlanRequestDTO;
import org.example.adventure_planner.dto.TravelPlanResponseDTO;
import org.example.adventure_planner.exception.ResourceNotFoundException;
import org.example.adventure_planner.model.TravelPlan;
import org.example.adventure_planner.model.UserAdventure;
import org.example.adventure_planner.repository.TravelPlanRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelPlanServiceImpl implements TravelPlanService {

    private final TravelPlanRepository travelPlanRepository;
    private final ValidationService validationService;

    public TravelPlanServiceImpl(TravelPlanRepository travelPlanRepository,
                                 ValidationService validationService) {
        this.travelPlanRepository = travelPlanRepository;
        this.validationService = validationService;
    }

    private TravelPlanResponseDTO toResponseDTO(TravelPlan travelPlan) {
        TravelPlanResponseDTO dto = new TravelPlanResponseDTO();
        dto.setId(travelPlan.getId());
        dto.setType(travelPlan.getType());
        dto.setDepartureLocation(travelPlan.getDepartureLocation());
        dto.setArrivalLocation(travelPlan.getArrivalLocation());
        dto.setDepartureTime(travelPlan.getDepartureTime());
        dto.setArrivalTime(travelPlan.getArrivalTime());
        dto.setNotes(travelPlan.getNotes());
        dto.setUserAdventureId(travelPlan.getUserAdventure() != null ? travelPlan.getUserAdventure().getId() : null);
        return dto;
    }

    private void mapRequestToEntity(TravelPlanRequestDTO dto, TravelPlan travelPlan) {
        travelPlan.setType(dto.getType());
        travelPlan.setDepartureLocation(dto.getDepartureLocation());
        travelPlan.setArrivalLocation(dto.getArrivalLocation());
        travelPlan.setDepartureTime(dto.getDepartureTime());
        travelPlan.setArrivalTime(dto.getArrivalTime());
        travelPlan.setNotes(dto.getNotes());

        if (dto.getUserAdventureId() != null) {
            UserAdventure userAdventure = new UserAdventure();
            userAdventure.setId(dto.getUserAdventureId());
            travelPlan.setUserAdventure(userAdventure);
        }
    }

    @Override
    public List<TravelPlanResponseDTO> getAllTravelPlans() {
        return travelPlanRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TravelPlanResponseDTO getTravelPlanById(Long id) {
        validationService.requireId(id);
        TravelPlan travelPlan = travelPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel plan not found"));
        return toResponseDTO(travelPlan);
    }

    @Override
    public TravelPlanResponseDTO addTravelPlan(TravelPlanRequestDTO dto) {
        validationService.requireNotNull(dto, "Travel plan cannot be null");
        TravelPlan travelPlan = new TravelPlan();
        mapRequestToEntity(dto, travelPlan);
        TravelPlan saved = travelPlanRepository.save(travelPlan);
        return toResponseDTO(saved);
    }

    @Override
    public TravelPlanResponseDTO updateTravelPlan(Long id, TravelPlanRequestDTO dto) {
        validationService.requireId(id);
        TravelPlan travelPlan = travelPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Travel plan not found"));
        mapRequestToEntity(dto, travelPlan);
        TravelPlan saved = travelPlanRepository.save(travelPlan);
        return toResponseDTO(saved);
    }

    @Override
    public void deleteTravelPlan(Long id) {
        validationService.requireId(id);
        if (!travelPlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Travel plan not found");
        }
        travelPlanRepository.deleteById(id);
    }

    @Override
    public List<TravelPlanResponseDTO> getTravelPlansByUserAdventureId(Long userAdventureId) {
        validationService.requireId(userAdventureId);
        return travelPlanRepository.findByUserAdventure_Id(userAdventureId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
