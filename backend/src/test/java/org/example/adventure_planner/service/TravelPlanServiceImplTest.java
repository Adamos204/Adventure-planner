package org.example.adventure_planner.service;

import org.example.adventure_planner.model.TravelPlan;
import org.example.adventure_planner.repository.TravelPlanRepository;
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
class TravelPlanServiceImplTest {

    private static final String TRAVEL_PLAN_NOT_FOUND_MSG = "Travel plan not found";
    private static final String TRAVEL_PLAN_CANNOT_BE_NULL_MSG = "Travel plan cannot be null";

    @Mock
    private TravelPlanRepository travelPlanRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private TravelPlanServiceImpl travelPlanService;

    private TravelPlan sampleTravelPlan;

    @BeforeEach
    void setUp() {
        sampleTravelPlan = new TravelPlan();
        sampleTravelPlan.setId(1L);
        sampleTravelPlan.setType("Sample Travel Plan");
    }

    @Test
    void getAllTravelPlans_returnsList() {
        when(travelPlanRepository.findAll()).thenReturn(Arrays.asList(sampleTravelPlan));

        List<TravelPlan> travelPlans = travelPlanService.getAllTravelPlans();

        assertEquals(1, travelPlans.size());
        assertEquals(sampleTravelPlan, travelPlans.get(0));
        verify(travelPlanRepository).findAll();
    }

    @Test
    void getTravelPlanById_existingId_returnsTravelPlan() {
        when(travelPlanRepository.existsById(1L)).thenReturn(true);
        when(travelPlanRepository.findById(1L)).thenReturn(Optional.of(sampleTravelPlan));

        Optional<TravelPlan> result = travelPlanService.getTravelPlanById(1L);

        assertTrue(result.isPresent());
        assertEquals(sampleTravelPlan, result.get());

        verify(validationService).requireId(1L);
        verify(travelPlanRepository).existsById(1L);
        verify(validationService).requireEntityExists(true, "Travel plan with ID 1 not found");
        verify(travelPlanRepository).findById(1L);
    }

    @Test
    void getTravelPlanById_nonExistingId_throwsException() {
        when(travelPlanRepository.existsById(1L)).thenReturn(false);

        doThrow(new IllegalStateException("Travel plan with ID 1 not found"))
                .when(validationService).requireEntityExists(false, "Travel plan with ID 1 not found");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            travelPlanService.getTravelPlanById(1L);
        });

        assertEquals("Travel plan with ID 1 not found", exception.getMessage());

        verify(validationService).requireId(1L);
        verify(travelPlanRepository).existsById(1L);
        verify(validationService).requireEntityExists(false, "Travel plan with ID 1 not found");
        verify(travelPlanRepository, never()).findById(any());
    }

    @Test
    void addTravelPlan_valid_returnsSavedTravelPlan() {
        when(travelPlanRepository.save(sampleTravelPlan)).thenReturn(sampleTravelPlan);

        TravelPlan result = travelPlanService.addTravelPlan(sampleTravelPlan);

        assertEquals(sampleTravelPlan, result);
        verify(validationService).requireNotNull(sampleTravelPlan, TRAVEL_PLAN_CANNOT_BE_NULL_MSG);
        verify(travelPlanRepository).save(sampleTravelPlan);
    }

    @Test
    void addTravelPlan_null_throwsException() {
        doThrow(new IllegalArgumentException(TRAVEL_PLAN_CANNOT_BE_NULL_MSG))
                .when(validationService).requireNotNull(null, TRAVEL_PLAN_CANNOT_BE_NULL_MSG);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            travelPlanService.addTravelPlan(null);
        });

        assertEquals(TRAVEL_PLAN_CANNOT_BE_NULL_MSG, exception.getMessage());
        verify(validationService).requireNotNull(null, TRAVEL_PLAN_CANNOT_BE_NULL_MSG);
        verify(travelPlanRepository, never()).save(any());
    }

    @Test
    void updateTravelPlan_valid_returnsUpdatedTravelPlan() {
        when(travelPlanRepository.existsById(sampleTravelPlan.getId())).thenReturn(true);
        when(travelPlanRepository.save(sampleTravelPlan)).thenReturn(sampleTravelPlan);

        TravelPlan result = travelPlanService.updateTravelPlan(sampleTravelPlan);

        assertEquals(sampleTravelPlan, result);

        verify(validationService).requireNotNull(sampleTravelPlan, TRAVEL_PLAN_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(sampleTravelPlan.getId());
        verify(travelPlanRepository).existsById(sampleTravelPlan.getId());
        verify(validationService).requireEntityExists(true, TRAVEL_PLAN_NOT_FOUND_MSG);
        verify(travelPlanRepository).save(sampleTravelPlan);
    }

    @Test
    void updateTravelPlan_travelPlanNotFound_throwsException() {
        when(travelPlanRepository.existsById(sampleTravelPlan.getId())).thenReturn(false);

        doThrow(new IllegalStateException(TRAVEL_PLAN_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, TRAVEL_PLAN_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            travelPlanService.updateTravelPlan(sampleTravelPlan);
        });

        assertEquals(TRAVEL_PLAN_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireNotNull(sampleTravelPlan, TRAVEL_PLAN_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(sampleTravelPlan.getId());
        verify(travelPlanRepository).existsById(sampleTravelPlan.getId());
        verify(validationService).requireEntityExists(false, TRAVEL_PLAN_NOT_FOUND_MSG);
        verify(travelPlanRepository, never()).save(any());
    }

    @Test
    void deleteTravelPlan_existingTravelPlan_doesNotThrow() {
        when(travelPlanRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> travelPlanService.deleteTravelPlan(1L));

        verify(validationService).requireId(1L);
        verify(travelPlanRepository).existsById(1L);
        verify(validationService).requireEntityExists(true, TRAVEL_PLAN_NOT_FOUND_MSG);
        verify(travelPlanRepository).deleteById(1L);
    }

    @Test
    void deleteTravelPlan_travelPlanNotFound_throwsException() {
        when(travelPlanRepository.existsById(1L)).thenReturn(false);

        doThrow(new IllegalStateException(TRAVEL_PLAN_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, TRAVEL_PLAN_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            travelPlanService.deleteTravelPlan(1L);
        });

        assertEquals(TRAVEL_PLAN_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireId(1L);
        verify(travelPlanRepository).existsById(1L);
        verify(validationService).requireEntityExists(false, TRAVEL_PLAN_NOT_FOUND_MSG);
        verify(travelPlanRepository, never()).deleteById(any());
    }

    @Test
    void getTravelPlansByUserAdventureId_returnsList() {
        when(travelPlanRepository.findByUserAdventureId(1L)).thenReturn(Arrays.asList(sampleTravelPlan));

        List<TravelPlan> result = travelPlanService.getTravelPlansByUserAdventureId(1L);

        assertEquals(1, result.size());
        assertEquals(sampleTravelPlan, result.get(0));

        verify(validationService).requireId(1L);
        verify(travelPlanRepository).findByUserAdventureId(1L);
    }
}