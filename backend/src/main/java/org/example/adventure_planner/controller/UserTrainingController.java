package org.example.adventure_planner.controller;

import org.example.adventure_planner.dto.UserTrainingRequestDTO;
import org.example.adventure_planner.dto.UserTrainingResponseDTO;
import org.example.adventure_planner.service.UserTrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-trainings")
public class UserTrainingController {

    private final UserTrainingService userTrainingService;

    public UserTrainingController(UserTrainingService userTrainingService) {
        this.userTrainingService = userTrainingService;
    }

    @GetMapping
    public ResponseEntity<List<UserTrainingResponseDTO>> getAllTrainings() {
        List<UserTrainingResponseDTO> trainings = userTrainingService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTrainingResponseDTO> getTrainingById(@PathVariable Long id) {
        UserTrainingResponseDTO training = userTrainingService.getTrainingById(id);
        return ResponseEntity.ok(training);
    }

    @PostMapping
    public ResponseEntity<UserTrainingResponseDTO> addTraining(@RequestBody UserTrainingRequestDTO dto) {
        UserTrainingResponseDTO saved = userTrainingService.addTraining(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTrainingResponseDTO> updateTraining(
            @PathVariable Long id,
            @RequestBody UserTrainingRequestDTO dto) {
        UserTrainingResponseDTO updated = userTrainingService.updateTraining(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        userTrainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTrainingResponseDTO>> getAllUserTrainings(@PathVariable Long userId) {
        List<UserTrainingResponseDTO> trainings = userTrainingService.getAllUserTrainings(userId);
        return ResponseEntity.ok(trainings);
    }
}