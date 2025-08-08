package org.example.adventure_planner.controller;

import org.example.adventure_planner.model.UserAdventure;
import org.example.adventure_planner.service.UserAdventureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

@ExtendWith(MockitoExtension.class)
class UserAdventureControllerTest {

    @Mock
    private UserAdventureService userAdventureService;

    @InjectMocks
    private UserAdventureController userAdventureController;

    private UserAdventure sampleUserAdventure;

    @BeforeEach
    void setUp(){
        sampleUserAdventure = new UserAdventure();
        sampleUserAdventure.setId(1L);
        sampleUserAdventure.setName("testadventure");
    }

    @Test
    void testGetAllUserAdventures(){
        when(userAdventureService.getAllAdventures()).thenReturn(Arrays.asList(sampleUserAdventure));

        ResponseEntity<List<UserAdventure>> response = userAdventureController.getAllAdventures();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(userAdventureService, times(1)).getAllAdventures();
    }

    @Test
    void testAddUser() {
        when(userAdventureService.addAdventure(any(UserAdventure.class))).thenReturn(sampleUserAdventure);

        ResponseEntity<UserAdventure> response = userAdventureController.addAdventure(sampleUserAdventure);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleUserAdventure, response.getBody());
        verify(userAdventureService, times(1)).addAdventure(sampleUserAdventure);
    }

    @Test
    void testGetUserAdventureById(){
        when(userAdventureService.getAdventureById(1L)).thenReturn(Optional.of(sampleUserAdventure));

        ResponseEntity<UserAdventure> response = userAdventureController.getAdventureById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleUserAdventure, response.getBody());
        verify(userAdventureService, times(1)).getAdventureById(1L);
    }

    @Test
    void testUpdateUser() {
        when(userAdventureService.updateAdventure(any(UserAdventure.class))).thenReturn(sampleUserAdventure);

        ResponseEntity<UserAdventure> response = userAdventureController.updateAdventure(sampleUserAdventure);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleUserAdventure, response.getBody());
        verify(userAdventureService, times(1)).updateAdventure(sampleUserAdventure);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userAdventureService).deleteAdventure(1L);

        ResponseEntity<Void> response = userAdventureController.deleteAdventure(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userAdventureService, times(1)).deleteAdventure(1L);
    }

    @Test
    void testGetAllUsersAdventures() {
        Long userId = 1L;
        List<UserAdventure> adventures = Arrays.asList(sampleUserAdventure);

        when(userAdventureService.getAllUsersAdventures(userId)).thenReturn(adventures);

        ResponseEntity<List<UserAdventure>> response = userAdventureController.getAllUsersAdventures(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adventures, response.getBody());
        verify(userAdventureService, times(1)).getAllUsersAdventures(userId);
    }
}