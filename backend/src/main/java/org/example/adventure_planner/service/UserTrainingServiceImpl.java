package org.example.adventure_planner.service;

import org.example.adventure_planner.model.UserTraining;
import org.example.adventure_planner.repository.UserTrainingRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTrainingServiceImpl implements UserTrainingService {

    private final UserTrainingRepository userTrainingRepository;
    private final ValidationService validationService;

    public UserTrainingServiceImpl(UserTrainingRepository userTrainingRepository, ValidationService validationService) {
        this.userTrainingRepository = userTrainingRepository;
        this.validationService = validationService;
    }

    @Override
    public List<UserTraining> getAllTrainings() {
        return userTrainingRepository.findAll();
    }

    @Override
    public Optional<UserTraining> getTrainingById(Long id) {
        validationService.requireId(id);
        validationService.requireEntityExists(userTrainingRepository.existsById(id),
                "Training with ID " + id + " not found");
        return userTrainingRepository.findById(id);
    }

    @Override
    public UserTraining addTraining(UserTraining userTraining) {
        validationService.requireNotNull(userTraining, "Training cannot be null");
        return userTrainingRepository.save(userTraining);
    }

    @Override
    public UserTraining updateTraining(UserTraining userTraining) {
        validationService.requireNotNull(userTraining, "Training cannot be null");
        validationService.requireId(userTraining.getId());
        validationService.requireEntityExists(userTrainingRepository.existsById(userTraining.getId()),
                "Training not found");
        return userTrainingRepository.save(userTraining);
    }

    @Override
    public void deleteTraining(Long id) {
        validationService.requireId(id);
        validationService.requireEntityExists(userTrainingRepository.existsById(id),
                "Training not found");
        userTrainingRepository.deleteById(id);
    }

    @Override
    public List<UserTraining> getAllUserTrainings(Long userId) {
        validationService.requireId(userId);
        return userTrainingRepository.findByUserId(userId);
    }
}

