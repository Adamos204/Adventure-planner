package org.example.adventure_planner.controller;

import org.example.adventure_planner.dto.TravelPlanRequestDTO;
import org.example.adventure_planner.dto.TravelPlanResponseDTO;
import org.example.adventure_planner.service.TravelPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travel-plan")
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    public TravelPlanController(TravelPlanService travelPlanService) {
        this.travelPlanService = travelPlanService;
    }

    @GetMapping
    public ResponseEntity<List<TravelPlanResponseDTO>> getAllTravelPlans() {
        return ResponseEntity.ok(travelPlanService.getAllTravelPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelPlanResponseDTO> getTravelPlan(@PathVariable Long id) {
        return ResponseEntity.ok(travelPlanService.getTravelPlanById(id));
    }

    @PostMapping
    public ResponseEntity<TravelPlanResponseDTO> addTravelPlan(@RequestBody TravelPlanRequestDTO dto) {
        return ResponseEntity.ok(travelPlanService.addTravelPlan(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelPlanResponseDTO> updateTravelPlan(@PathVariable Long id,
                                                                  @RequestBody TravelPlanRequestDTO dto) {
        return ResponseEntity.ok(travelPlanService.updateTravelPlan(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravelPlan(@PathVariable Long id) {
        travelPlanService.deleteTravelPlan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/adventure/{userAdventureId}")
    public ResponseEntity<List<TravelPlanResponseDTO>> getTravelPlansByUserAdventureId(@PathVariable Long userAdventureId) {
        return ResponseEntity.ok(travelPlanService.getTravelPlansByUserAdventureId(userAdventureId));
    }
}
