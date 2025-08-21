/*package org.example.adventure_planner.service;

import org.example.adventure_planner.model.UserTraining;
import org.example.adventure_planner.repository.UserTrainingRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTrainingServiceImplTest {

    private static final String TRAINING_NOT_FOUND_MSG = "Training not found";
    private static final String TRAINING_CANNOT_BE_NULL_MSG = "Training cannot be null";

    @Mock
    private UserTrainingRepository userTrainingRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private UserTrainingServiceImpl userTrainingService;

    private UserTraining sampleTraining;

    @BeforeEach
    void setUp() {
        sampleTraining = new UserTraining();
        sampleTraining.setId(1L);
        sampleTraining.setType("Gym");
    }

    @Test
    void getAllTrainings_returnsList() {
        when(userTrainingRepository.findAll()).thenReturn(Arrays.asList(sampleTraining));

        List<UserTraining> trainings = userTrainingService.getAllTrainings();

        assertEquals(1, trainings.size());
        assertEquals(sampleTraining, trainings.get(0));
        verify(userTrainingRepository).findAll();
    }

    @Test
    void getTrainingById_existingId_returnsTraining() {
        when(userTrainingRepository.existsById(1L)).thenReturn(true);
        when(userTrainingRepository.findById(1L)).thenReturn(Optional.of(sampleTraining));

        Optional<UserTraining> result = userTrainingService.getTrainingById(1L);

        assertTrue(result.isPresent());
        assertEquals(sampleTraining, result.get());

        verify(validationService).requireId(1L);
        verify(userTrainingRepository).existsById(1L);
        verify(validationService).requireEntityExists(true, "Training with ID 1 not found");
        verify(userTrainingRepository).findById(1L);
    }

    @Test
    void getTrainingById_nonExistingId_throwsException() {
        when(userTrainingRepository.existsById(1L)).thenReturn(false);

        doThrow(new IllegalStateException("Training with ID 1 not found"))
                .when(validationService).requireEntityExists(false, "Training with ID 1 not found");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userTrainingService.getTrainingById(1L);
        });

        assertEquals("Training with ID 1 not found", exception.getMessage());

        verify(validationService).requireId(1L);
        verify(userTrainingRepository).existsById(1L);
        verify(validationService).requireEntityExists(false, "Training with ID 1 not found");
        verify(userTrainingRepository, never()).findById(any());
    }

    @Test
    void addTraining_valid_returnsSavedTraining() {
        when(userTrainingRepository.save(sampleTraining)).thenReturn(sampleTraining);

        UserTraining result = userTrainingService.addTraining(sampleTraining);

        assertEquals(sampleTraining, result);
        verify(validationService).requireNotNull(sampleTraining, TRAINING_CANNOT_BE_NULL_MSG);
        verify(userTrainingRepository).save(sampleTraining);
    }

    @Test
    void addTraining_null_throwsException() {
        doThrow(new IllegalArgumentException(TRAINING_CANNOT_BE_NULL_MSG))
                .when(validationService).requireNotNull(null, TRAINING_CANNOT_BE_NULL_MSG);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userTrainingService.addTraining(null);
        });

        assertEquals(TRAINING_CANNOT_BE_NULL_MSG, exception.getMessage());
        verify(validationService).requireNotNull(null, TRAINING_CANNOT_BE_NULL_MSG);
        verify(userTrainingRepository, never()).save(any());
    }

    @Test
    void updateTraining_valid_returnsUpdatedTraining() {
        when(userTrainingRepository.existsById(sampleTraining.getId())).thenReturn(true);
        when(userTrainingRepository.save(sampleTraining)).thenReturn(sampleTraining);

        UserTraining result = userTrainingService.updateTraining(sampleTraining);

        assertEquals(sampleTraining, result);

        verify(validationService).requireNotNull(sampleTraining, TRAINING_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(sampleTraining.getId());
        verify(userTrainingRepository).existsById(sampleTraining.getId());
        verify(validationService).requireEntityExists(true, TRAINING_NOT_FOUND_MSG);
        verify(userTrainingRepository).save(sampleTraining);
    }

    @Test
    void updateTraining_trainingNotFound_throwsException() {
        when(userTrainingRepository.existsById(sampleTraining.getId())).thenReturn(false);

        doThrow(new IllegalStateException(TRAINING_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, TRAINING_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userTrainingService.updateTraining(sampleTraining);
        });

        assertEquals(TRAINING_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireNotNull(sampleTraining, TRAINING_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(sampleTraining.getId());
        verify(userTrainingRepository).existsById(sampleTraining.getId());
        verify(validationService).requireEntityExists(false, TRAINING_NOT_FOUND_MSG);
        verify(userTrainingRepository, never()).save(any());
    }

    @Test
    void deleteTraining_existingTraining_doesNotThrow() {
        when(userTrainingRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> userTrainingService.deleteTraining(1L));

        verify(validationService).requireId(1L);
        verify(userTrainingRepository).existsById(1L);
        verify(validationService).requireEntityExists(true, TRAINING_NOT_FOUND_MSG);
        verify(userTrainingRepository).deleteById(1L);
    }

    @Test
    void deleteTraining_trainingNotFound_throwsException() {
        when(userTrainingRepository.existsById(1L)).thenReturn(false);

        doThrow(new IllegalStateException(TRAINING_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, TRAINING_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userTrainingService.deleteTraining(1L);
        });

        assertEquals(TRAINING_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireId(1L);
        verify(userTrainingRepository).existsById(1L);
        verify(validationService).requireEntityExists(false, TRAINING_NOT_FOUND_MSG);
        verify(userTrainingRepository, never()).deleteById(any());
    }

    @Test
    void getAllUserTrainings_returnsList() {
        when(userTrainingRepository.findByUserId(1L)).thenReturn(Arrays.asList(sampleTraining));

        List<UserTraining> result = userTrainingService.getAllUserTrainings(1L);

        assertEquals(1, result.size());
        assertEquals(sampleTraining, result.get(0));

        verify(validationService).requireId(1L);
        verify(userTrainingRepository).findByUserId(1L);
    }
}*/