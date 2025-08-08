package org.example.adventure_planner.service;

import org.example.adventure_planner.model.User;
import org.example.adventure_planner.repository.UserRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private UserServiceImpl userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setUsername("testuser");
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(sampleUser));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(sampleUser, result.get());
        verify(validationService, times(1)).requireId(1L);
        verify(validationService, times(1)).requireEntityExists(true, "User not found");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testAddUser() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User saved = userService.addUser(sampleUser);

        assertEquals(sampleUser, saved);
        verify(validationService, times(1)).requireNotNull(sampleUser, "User cannot be null");
        verify(userRepository, times(1)).save(sampleUser);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User updated = userService.updateUser(sampleUser);

        assertEquals(sampleUser, updated);
        verify(validationService, times(1)).requireId(1L);
        verify(validationService, times(1)).requireNotNull(sampleUser, "User cannot be null");
        verify(validationService, times(1)).requireEntityExists(true, "User not found");
        verify(userRepository, times(1)).save(sampleUser);
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(validationService, times(1)).requireId(1L);
        verify(validationService, times(1)).requireEntityExists(true, "User not found");
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetUserById_UserNotFound() {
        Long userId = 1L;

        doNothing().when(validationService).requireId(userId);
        when(userRepository.existsById(userId)).thenReturn(false);

        // validationService.requireEntityExists throws exception if false, so we simulate it
        doThrow(new RuntimeException("User not found"))
                .when(validationService).requireEntityExists(false, "User not found");

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(userId);
        });

        assertEquals("User not found", thrown.getMessage());

        verify(validationService).requireId(userId);
        verify(validationService).requireEntityExists(false, "User not found");
        verify(userRepository, never()).findById(userId);
    }

    @Test
    void testAddUser_NullUser() {
        doThrow(new IllegalArgumentException("User cannot be null"))
                .when(validationService).requireNotNull(null, "User cannot be null");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(null);
        });

        assertEquals("User cannot be null", thrown.getMessage());

        verify(validationService).requireNotNull(null, "User cannot be null");
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdateUser_NullUser() {
        doThrow(new IllegalArgumentException("User cannot be null"))
                .when(validationService).requireNotNull(null, "User cannot be null");

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(null);
        });

        assertEquals("User cannot be null", thrown.getMessage());

        verify(validationService).requireNotNull(null, "User cannot be null");
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        sampleUser.setId(1L);

        doNothing().when(validationService).requireId(sampleUser.getId());
        doNothing().when(validationService).requireNotNull(sampleUser, "User cannot be null");
        when(userRepository.existsById(sampleUser.getId())).thenReturn(false);

        doThrow(new RuntimeException("User not found"))
                .when(validationService).requireEntityExists(false, "User not found");

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.updateUser(sampleUser);
        });

        assertEquals("User not found", thrown.getMessage());

        verify(validationService).requireId(sampleUser.getId());
        verify(validationService).requireNotNull(sampleUser, "User cannot be null");
        verify(validationService).requireEntityExists(false, "User not found");
        verify(userRepository, never()).save(any());
    }

    @Test
    void testDeleteUser_UserNotFound() {
        Long userId = 1L;

        doNothing().when(validationService).requireId(userId);
        when(userRepository.existsById(userId)).thenReturn(false);

        doThrow(new RuntimeException("User not found"))
                .when(validationService).requireEntityExists(false, "User not found");

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(userId);
        });

        assertEquals("User not found", thrown.getMessage());

        verify(validationService).requireId(userId);
        verify(validationService).requireEntityExists(false, "User not found");
        verify(userRepository, never()).deleteById(userId);
    }
}