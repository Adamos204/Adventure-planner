package org.example.adventure_planner.service;
import org.example.adventure_planner.dto.UserTrainingRequestDTO;
import org.example.adventure_planner.dto.UserTrainingResponseDTO;
import org.example.adventure_planner.exception.ResourceNotFoundException;
import org.example.adventure_planner.model.User;
import org.example.adventure_planner.model.UserTraining;
import org.example.adventure_planner.repository.UserTrainingRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTrainingServiceImpl implements UserTrainingService {

    private final UserTrainingRepository userTrainingRepository;
    private final ValidationService validationService;

    public UserTrainingServiceImpl(UserTrainingRepository userTrainingRepository, ValidationService validationService) {
        this.userTrainingRepository = userTrainingRepository;
        this.validationService = validationService;
    }

    private UserTrainingResponseDTO toResponseDTO(UserTraining training) {
        UserTrainingResponseDTO dto = new UserTrainingResponseDTO();
        dto.setId(training.getId());
        dto.setDate(training.getDate());
        dto.setType(training.getType());
        dto.setDurationInMin(training.getDurationInMin());
        dto.setDurationInKm(training.getDurationInKm());
        dto.setNotes(training.getNotes());
        dto.setUserId(training.getUser() != null ? training.getUser().getId() : null);
        return dto;
    }

    private void mapRequestToEntity(UserTrainingRequestDTO dto, UserTraining training) {
        training.setDate(dto.getDate());
        training.setType(dto.getType());
        training.setDurationInMin(dto.getDurationInMin());
        training.setDurationInKm(dto.getDurationInKm());
        training.setNotes(dto.getNotes());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            training.setUser(user);
        }
    }

    @Override
    public List<UserTrainingResponseDTO> getAllTrainings() {
        return userTrainingRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserTrainingResponseDTO getTrainingById(Long id) {
        validationService.requireId(id);
        UserTraining training = userTrainingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training not found"));
        return toResponseDTO(training);
    }

    @Override
    public UserTrainingResponseDTO addTraining(UserTrainingRequestDTO dto) {
        validationService.requireNotNull(dto, "Training cannot be null");
        UserTraining training = new UserTraining();
        mapRequestToEntity(dto, training);
        UserTraining saved = userTrainingRepository.save(training);
        return toResponseDTO(saved);
    }

    @Override
    public UserTrainingResponseDTO updateTraining(Long id, UserTrainingRequestDTO dto) {
        validationService.requireId(id);
        UserTraining training = userTrainingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Training not found"));
        mapRequestToEntity(dto, training);
        UserTraining saved = userTrainingRepository.save(training);
        return toResponseDTO(saved);
    }

    @Override
    public void deleteTraining(Long id) {
        validationService.requireId(id);
        if (!userTrainingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Training not found");
        }
        userTrainingRepository.deleteById(id);
    }

    @Override
    public List<UserTrainingResponseDTO> getAllUserTrainings(Long userId) {
        validationService.requireId(userId);
        return userTrainingRepository.findByUser_Id(userId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
