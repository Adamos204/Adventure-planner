package org.example.adventure_planner.service;

import org.example.adventure_planner.model.User;
import org.example.adventure_planner.model.UserAdventure;
import org.example.adventure_planner.repository.UserAdventureRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAdventureServiceImplTest {

    @Mock
    private UserAdventureRepository userAdventureRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private UserAdventureServiceImpl userAdventureService;

    private UserAdventure sampleUserAdventure;

    @BeforeEach
    void setup(){
        sampleUserAdventure = new UserAdventure();
        sampleUserAdventure.setId(1L);
        sampleUserAdventure.setName("testuseradventure");
    }

    @Test
    void testGetAllUsers() {
        when(userAdventureRepository.findAll()).thenReturn(Arrays.asList(sampleUserAdventure));

        List<UserAdventure> userAdventures = userAdventureService.getAllAdventures();

        assertEquals(1, userAdventures.size());
        verify(userAdventureRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(userAdventureRepository.existsById(1L)).thenReturn(true);
        when(userAdventureRepository.findById(1L)).thenReturn(Optional.of(sampleUserAdventure));

        Optional<UserAdventure> result = userAdventureService.getAdventureById(1L);

        assertTrue(result.isPresent());
        assertEquals(sampleUserAdventure, result.get());
        verify(validationService, times(1)).requireId(1L);
        verify(validationService, times(1)).requireEntityExists(eq(true), anyString());
        verify(userAdventureRepository, times(1)).findById(1L);
    }

    @Test
    void testAddUser() {
        when(userAdventureRepository.save(sampleUserAdventure)).thenReturn(sampleUserAdventure);

        UserAdventure saved = userAdventureService.addAdventure(sampleUserAdventure);

        assertEquals(sampleUserAdventure, saved);
        verify(validationService, times(1)).requireNotNull(eq(sampleUserAdventure), anyString());
        verify(userAdventureRepository, times(1)).save(sampleUserAdventure);
    }

    @Test
    void testUpdateUser() {
        when(userAdventureRepository.existsById(1L)).thenReturn(true);
        when(userAdventureRepository.save(sampleUserAdventure)).thenReturn(sampleUserAdventure);

        UserAdventure updated = userAdventureService.updateAdventure(sampleUserAdventure);

        assertEquals(sampleUserAdventure, updated);
        verify(validationService, times(1)).requireId(1L);
        verify(validationService, times(1)).requireNotNull(eq(sampleUserAdventure), anyString());
        verify(validationService, times(1)).requireEntityExists(eq(true), anyString());
        verify(userAdventureRepository, times(1)).save(sampleUserAdventure);
    }

    @Test
    void testDeleteUser() {
        when(userAdventureRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userAdventureRepository).deleteById(1L);

        userAdventureService.deleteAdventure(1L);

        verify(validationService, times(1)).requireId(1L);
        verify(validationService, times(1)).requireEntityExists(eq(true), anyString());
        verify(userAdventureRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllUsersAdventures() {
        Long userId = 1L;
        List<UserAdventure> adventures = Arrays.asList(sampleUserAdventure);

        doNothing().when(validationService).requireId(userId);
        when(userAdventureRepository.findByUserId(userId)).thenReturn(adventures);

        List<UserAdventure> result = userAdventureService.getAllUsersAdventures(userId);

        assertEquals(adventures, result);
        verify(validationService, times(1)).requireId(userId);
        verify(userAdventureRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetAllUsersAdventures_InvalidUserId_Null() {
        Long invalidUserId = null;

        doThrow(new IllegalArgumentException("Invalid ID")).when(validationService).requireId(invalidUserId);

        assertThrows(IllegalArgumentException.class, () -> userAdventureService.getAllUsersAdventures(invalidUserId));

        verify(validationService, times(1)).requireId(invalidUserId);
        verify(userAdventureRepository, never()).findByUserId(any());
    }


    @Test
    void testAddAdventure_NullAdventure_ThrowsException() {
        UserAdventure nullAdventure = null;

        doThrow(new IllegalArgumentException("Adventure cannot be null"))
                .when(validationService).requireNotNull(nullAdventure, "Adventure cannot be null");

        assertThrows(IllegalArgumentException.class, () -> userAdventureService.addAdventure(nullAdventure));

        verify(validationService, times(1)).requireNotNull(nullAdventure, "Adventure cannot be null");
        verify(userAdventureRepository, never()).save(any());
    }

    @Test
    void testDeleteAdventure_NonExistingId_ThrowsException() {
        Long id = 195L;

        doNothing().when(validationService).requireId(id);
        when(userAdventureRepository.existsById(id)).thenReturn(false);
        doThrow(new IllegalArgumentException("Adventure not found"))
                .when(validationService).requireEntityExists(false, "Adventure not found");

        assertThrows(IllegalArgumentException.class, () -> userAdventureService.deleteAdventure(id));

        verify(validationService, times(1)).requireId(id);
        verify(userAdventureRepository, times(1)).existsById(id);
        verify(validationService, times(1)).requireEntityExists(false, "Adventure not found");
        verify(userAdventureRepository, never()).deleteById(any());
    }

}