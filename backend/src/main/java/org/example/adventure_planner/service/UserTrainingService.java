package org.example.adventure_planner.service;

import org.example.adventure_planner.model.UserTraining;
import java.util.List;
import java.util.Optional;

public interface UserTrainingService {
    List<UserTraining> getAllTrainings();
    Optional<UserTraining> getTrainingById(Long id);
    UserTraining addTraining(UserTraining userTraining);
    UserTraining updateTraining(UserTraining userTraining);
    void deleteTraining(Long id);
    List<UserTraining> getAllUserTrainings(Long userId);
}

