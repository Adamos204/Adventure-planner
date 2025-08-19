package org.example.adventure_planner.service;

import org.example.adventure_planner.dto.UserTrainingRequestDTO;
import org.example.adventure_planner.dto.UserTrainingResponseDTO;

import java.util.List;

public interface UserTrainingService {
    List<UserTrainingResponseDTO> getAllTrainings();
    UserTrainingResponseDTO getTrainingById(Long id);
    UserTrainingResponseDTO addTraining(UserTrainingRequestDTO dto);
    UserTrainingResponseDTO updateTraining(Long id, UserTrainingRequestDTO dto);
    void deleteTraining(Long id);
    List<UserTrainingResponseDTO> getAllUserTrainings(Long userId);
}
