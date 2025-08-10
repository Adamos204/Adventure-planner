package org.example.adventure_planner.controller;

import org.example.adventure_planner.model.TravelPlan;
import org.example.adventure_planner.service.TravelPlanService;
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
class TravelPlanControllerTest {

    @Mock
    private TravelPlanService travelPlanService;

    @InjectMocks
    private TravelPlanController travelPlanController;

    private TravelPlan sampleTravelPlan;

    @BeforeEach
    void setUp() {
        sampleTravelPlan = new TravelPlan();
        sampleTravelPlan.setId(1L);
        sampleTravelPlan.setType("Sample Travel Plan");
    }

    @Test
    void testGetAllTravelPlans() {
        when(travelPlanService.getAllTravelPlans()).thenReturn(Arrays.asList(sampleTravelPlan));

        ResponseEntity<List<TravelPlan>> response = travelPlanController.getAllTravelPlans();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(travelPlanService, times(1)).getAllTravelPlans();
    }

    @Test
    void testGetTravelPlanById_Found() {
        when(travelPlanService.getTravelPlanById(1L)).thenReturn(Optional.of(sampleTravelPlan));

        ResponseEntity<TravelPlan> response = travelPlanController.getTravelPlanById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleTravelPlan, response.getBody());
        verify(travelPlanService, times(1)).getTravelPlanById(1L);
    }

    @Test
    void testGetTravelPlanById_NotFound() {
        Long id = 1L;
        when(travelPlanService.getTravelPlanById(id)).thenReturn(Optional.empty());

        ResponseEntity<TravelPlan> response = travelPlanController.getTravelPlanById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(travelPlanService, times(1)).getTravelPlanById(id);
    }

    @Test
    void testAddTravelPlan() {
        when(travelPlanService.addTravelPlan(any(TravelPlan.class))).thenReturn(sampleTravelPlan);

        ResponseEntity<TravelPlan> response = travelPlanController.addTravelPlan(sampleTravelPlan);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleTravelPlan, response.getBody());
        verify(travelPlanService, times(1)).addTravelPlan(sampleTravelPlan);
    }

    @Test
    void testUpdateTravelPlan() {
        when(travelPlanService.updateTravelPlan(any(TravelPlan.class))).thenReturn(sampleTravelPlan);

        ResponseEntity<TravelPlan> response = travelPlanController.updateTravelPlan(sampleTravelPlan);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleTravelPlan, response.getBody());
        verify(travelPlanService, times(1)).updateTravelPlan(sampleTravelPlan);
    }

    @Test
    void testDeleteTravelPlan() {
        doNothing().when(travelPlanService).deleteTravelPlan(1L);

        ResponseEntity<Void> response = travelPlanController.deleteTravelPlan(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(travelPlanService, times(1)).deleteTravelPlan(1L);
    }

    @Test
    void testGetTravelPlansByUserAdventureId() {
        Long adventureId = 42L;
        when(travelPlanService.getTravelPlansByUserAdventureId(adventureId)).thenReturn(Arrays.asList(sampleTravelPlan));

        ResponseEntity<List<TravelPlan>> response = travelPlanController.getTravelPlansByUserAdventureId(adventureId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(travelPlanService, times(1)).getTravelPlansByUserAdventureId(adventureId);
    }
}