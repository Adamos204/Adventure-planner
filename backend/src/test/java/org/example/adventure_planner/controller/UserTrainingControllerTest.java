/*package org.example.adventure_planner.controller;

import org.example.adventure_planner.model.UserTraining;
import org.example.adventure_planner.service.UserTrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class UserTrainingControllerTest {

    @Mock
    private UserTrainingService userTrainingService;

    @InjectMocks
    private UserTrainingController userTrainingController;

    private UserTraining sampleUserTraining;

    @BeforeEach
    void setUp() {
        sampleUserTraining = new UserTraining();
        sampleUserTraining.setId(1L);
        sampleUserTraining.setType("Sample Training");
    }

    @Test
    void testGetAllTrainings() {
        when(userTrainingService.getAllTrainings()).thenReturn(Arrays.asList(sampleUserTraining));

        ResponseEntity<List<UserTraining>> response = userTrainingController.getAllTrainings();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userTrainingService, times(1)).getAllTrainings();
    }

    @Test
    void testGetTrainingById_Found() {
        when(userTrainingService.getTrainingById(1L)).thenReturn(Optional.of(sampleUserTraining));

        ResponseEntity<UserTraining> response = userTrainingController.getTrainingById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleUserTraining, response.getBody());
        verify(userTrainingService, times(1)).getTrainingById(1L);
    }

    @Test
    void testGetTrainingById_NotFound() {
        Long id = 1L;
        when(userTrainingService.getTrainingById(id)).thenReturn(Optional.empty());

        ResponseEntity<UserTraining> response = userTrainingController.getTrainingById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userTrainingService, times(1)).getTrainingById(id);
    }

    @Test
    void testAddTraining() {
        when(userTrainingService.addTraining(any(UserTraining.class))).thenReturn(sampleUserTraining);

        ResponseEntity<UserTraining> response = userTrainingController.addTraining(sampleUserTraining);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleUserTraining, response.getBody());
        verify(userTrainingService, times(1)).addTraining(sampleUserTraining);
    }

    @Test
    void testUpdateTraining() {
        when(userTrainingService.updateTraining(any(UserTraining.class))).thenReturn(sampleUserTraining);

        ResponseEntity<UserTraining> response = userTrainingController.updateTraining(sampleUserTraining.getId(), sampleUserTraining);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleUserTraining, response.getBody());
        verify(userTrainingService, times(1)).updateTraining(sampleUserTraining);
    }

    @Test
    void testDeleteTraining() {
        doNothing().when(userTrainingService).deleteTraining(1L);

        ResponseEntity<Void> response = userTrainingController.deleteTraining(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userTrainingService, times(1)).deleteTraining(1L);
    }

    @Test
    void testGetAllUserTrainings() {
        Long userId = 42L;
        when(userTrainingService.getAllUserTrainings(userId)).thenReturn(Arrays.asList(sampleUserTraining));

        ResponseEntity<List<UserTraining>> response = userTrainingController.getAllUserTrainings(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userTrainingService, times(1)).getAllUserTrainings(userId);
    }
}*/