package org.example.adventure_planner.controller;

import org.example.adventure_planner.model.UserTraining;
import org.example.adventure_planner.service.UserTrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-training")
public class UserTrainingController {

    private final UserTrainingService userTrainingService;

    public UserTrainingController(UserTrainingService userTrainingService) {
        this.userTrainingService = userTrainingService;
    }

    @GetMapping
    public ResponseEntity<List<UserTraining>> getAllTrainings() {
        return ResponseEntity.ok(userTrainingService.getAllTrainings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTraining> getTrainingById(@PathVariable Long id) {
        UserTraining training = userTrainingService.getTrainingById(id)
                .orElseThrow(() -> new RuntimeException("Training with ID " + id + " not found"));
        return ResponseEntity.ok(training);
    }

    @PostMapping
    public ResponseEntity<UserTraining> addTraining(@RequestBody UserTraining userTraining) {
        return ResponseEntity.ok(userTrainingService.addTraining(userTraining));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTraining> updateTraining(@PathVariable Long id, @RequestBody UserTraining userTraining) {
        userTraining.setId(id);
        return ResponseEntity.ok(userTrainingService.updateTraining(userTraining));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        userTrainingService.deleteTraining(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTraining>> getAllUserTrainings(@PathVariable Long userId) {
        return ResponseEntity.ok(userTrainingService.getAllUserTrainings(userId));
    }
}

