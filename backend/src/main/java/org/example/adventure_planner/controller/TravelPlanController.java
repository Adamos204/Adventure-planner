package org.example.adventure_planner.controller;

import org.example.adventure_planner.model.TravelPlan;
import org.example.adventure_planner.service.TravelPlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("travel-plan")
public class TravelPlanController {

    private final TravelPlanService travelPlanService;

    public TravelPlanController(TravelPlanService travelPlanService){
        this.travelPlanService = travelPlanService;
    }

    @GetMapping
    public ResponseEntity<List<TravelPlan>> getAllTravelPlans(){
        return ResponseEntity.ok(travelPlanService.getAllTravelPlans());
    }

    @PostMapping
    public ResponseEntity<TravelPlan> addTravelPlan(@RequestBody TravelPlan travelPlan){
        TravelPlan saveTravelPlan = travelPlanService.addTravelPlan(travelPlan);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveTravelPlan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelPlan> getTravelPlanById(@PathVariable Long id){
        return travelPlanService.getTravelPlanById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping
    public ResponseEntity<TravelPlan> updateTravelPlan(@RequestBody TravelPlan travelPlan){
        return ResponseEntity.ok(travelPlanService.updateTravelPlan(travelPlan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravelPlan(@PathVariable Long id){
        travelPlanService.deleteTravelPlan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/adventure/{adventureId}")
    public ResponseEntity<List<TravelPlan>> getTravelPlansByUserAdventureId(@PathVariable Long adventureId){
        return ResponseEntity.ok(travelPlanService.getTravelPlansByUserAdventureId(adventureId));
    }
}
