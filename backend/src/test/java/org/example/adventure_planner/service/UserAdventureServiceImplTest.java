/*package org.example.adventure_planner.service;

import org.example.adventure_planner.model.UserAdventure;
import org.example.adventure_planner.repository.UserAdventureRepository;
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
class UserAdventureServiceImplTest {

    private static final String ADVENTURE_NOT_FOUND_MSG = "Adventure not found";
    private static final String ADVENTURE_CANNOT_BE_NULL_MSG = "Adventure cannot be null";

    @Mock
    private UserAdventureRepository userAdventureRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private UserAdventureServiceImpl userAdventureService;

    private UserAdventure sampleAdventure;

    @BeforeEach
    void setUp() {
        sampleAdventure = new UserAdventure();
        sampleAdventure.setId(1L);
        sampleAdventure.setName("Sample Adventure");
    }

    @Test
    void getAllAdventures_returnsList() {
        when(userAdventureRepository.findAll()).thenReturn(Arrays.asList(sampleAdventure));

        List<UserAdventure> adventures = userAdventureService.getAllAdventures();

        assertEquals(1, adventures.size());
        assertEquals(sampleAdventure, adventures.get(0));
        verify(userAdventureRepository).findAll();
    }

    @Test
    void getAdventureById_existingId_returnsAdventure() {
        when(userAdventureRepository.existsById(1L)).thenReturn(true);
        when(userAdventureRepository.findById(1L)).thenReturn(Optional.of(sampleAdventure));

        Optional<UserAdventure> result = userAdventureService.getAdventureById(1L);

        assertTrue(result.isPresent());
        assertEquals(sampleAdventure, result.get());

        verify(validationService).requireId(1L);
        verify(userAdventureRepository).existsById(1L);
        verify(validationService).requireEntityExists(true, "Adventure with ID 1 not found");
        verify(userAdventureRepository).findById(1L);
    }

    @Test
    void getAdventureById_nonExistingId_throwsException() {
        when(userAdventureRepository.existsById(1L)).thenReturn(false);

        doThrow(new IllegalStateException("Adventure with ID 1 not found"))
                .when(validationService).requireEntityExists(false, "Adventure with ID 1 not found");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userAdventureService.getAdventureById(1L);
        });

        assertEquals("Adventure with ID 1 not found", exception.getMessage());

        verify(validationService).requireId(1L);
        verify(userAdventureRepository).existsById(1L);
        verify(validationService).requireEntityExists(false, "Adventure with ID 1 not found");
        verify(userAdventureRepository, never()).findById(any());
    }

    @Test
    void addAdventure_valid_returnsSavedAdventure() {
        when(userAdventureRepository.save(sampleAdventure)).thenReturn(sampleAdventure);

        UserAdventure result = userAdventureService.addAdventure(sampleAdventure);

        assertEquals(sampleAdventure, result);
        verify(validationService).requireNotNull(sampleAdventure, ADVENTURE_CANNOT_BE_NULL_MSG);
        verify(userAdventureRepository).save(sampleAdventure);
    }

    @Test
    void addAdventure_null_throwsException() {
        doThrow(new IllegalArgumentException(ADVENTURE_CANNOT_BE_NULL_MSG))
                .when(validationService).requireNotNull(null, ADVENTURE_CANNOT_BE_NULL_MSG);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userAdventureService.addAdventure(null);
        });

        assertEquals(ADVENTURE_CANNOT_BE_NULL_MSG, exception.getMessage());
        verify(validationService).requireNotNull(null, ADVENTURE_CANNOT_BE_NULL_MSG);
        verify(userAdventureRepository, never()).save(any());
    }

    @Test
    void updateAdventure_valid_returnsUpdatedAdventure() {
        when(userAdventureRepository.existsById(sampleAdventure.getId())).thenReturn(true);
        when(userAdventureRepository.save(sampleAdventure)).thenReturn(sampleAdventure);

        UserAdventure result = userAdventureService.updateAdventure(sampleAdventure);

        assertEquals(sampleAdventure, result);

        verify(validationService).requireNotNull(sampleAdventure, ADVENTURE_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(sampleAdventure.getId());
        verify(userAdventureRepository).existsById(sampleAdventure.getId());
        verify(validationService).requireEntityExists(true, ADVENTURE_NOT_FOUND_MSG);
        verify(userAdventureRepository).save(sampleAdventure);
    }

    @Test
    void updateAdventure_adventureNotFound_throwsException() {
        when(userAdventureRepository.existsById(sampleAdventure.getId())).thenReturn(false);

        doThrow(new IllegalStateException(ADVENTURE_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, ADVENTURE_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userAdventureService.updateAdventure(sampleAdventure);
        });

        assertEquals(ADVENTURE_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireNotNull(sampleAdventure, ADVENTURE_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(sampleAdventure.getId());
        verify(userAdventureRepository).existsById(sampleAdventure.getId());
        verify(validationService).requireEntityExists(false, ADVENTURE_NOT_FOUND_MSG);
        verify(userAdventureRepository, never()).save(any());
    }

    @Test
    void deleteAdventure_existingAdventure_doesNotThrow() {
        when(userAdventureRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> userAdventureService.deleteAdventure(1L));

        verify(validationService).requireId(1L);
        verify(userAdventureRepository).existsById(1L);
        verify(validationService).requireEntityExists(true, ADVENTURE_NOT_FOUND_MSG);
        verify(userAdventureRepository).deleteById(1L);
    }

    @Test
    void deleteAdventure_adventureNotFound_throwsException() {
        when(userAdventureRepository.existsById(1L)).thenReturn(false);

        doThrow(new IllegalStateException(ADVENTURE_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, ADVENTURE_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userAdventureService.deleteAdventure(1L);
        });

        assertEquals(ADVENTURE_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireId(1L);
        verify(userAdventureRepository).existsById(1L);
        verify(validationService).requireEntityExists(false, ADVENTURE_NOT_FOUND_MSG);
        verify(userAdventureRepository, never()).deleteById(any());
    }

    @Test
    void getAllUsersAdventures_returnsList() {
        when(userAdventureRepository.findByUserId(1L)).thenReturn(Arrays.asList(sampleAdventure));

        List<UserAdventure> result = userAdventureService.getAllUsersAdventures(1L);

        assertEquals(1, result.size());
        assertEquals(sampleAdventure, result.get(0));

        verify(validationService).requireId(1L);
        verify(userAdventureRepository).findByUserId(1L);
    }
}*/