/*package org.example.adventure_planner.service;

import org.example.adventure_planner.model.User;
import org.example.adventure_planner.repository.UserRepository;
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
class UserServiceImplTest {

    private static final String USER_NOT_FOUND_MSG = "User not found";
    private static final String USER_CANNOT_BE_NULL_MSG = "User cannot be null";

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
    void getAllUsers_returnsList() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(sampleUser));

        List<User> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals(sampleUser, users.get(0));
        verify(userRepository).findAll();
    }

    @Test
    void getUserById_existingId_returnsUser() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.findById(id)).thenReturn(Optional.of(sampleUser));

        Optional<User> result = userService.getUserById(id);

        assertTrue(result.isPresent());
        assertEquals(sampleUser, result.get());

        verify(validationService).requireId(id);
        verify(userRepository).existsById(id);
        verify(validationService).requireEntityExists(true, USER_NOT_FOUND_MSG);
        verify(userRepository).findById(id);
    }

    @Test
    void getUserById_nonExistingId_throwsException() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(false);

        doThrow(new IllegalStateException(USER_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, USER_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.getUserById(id);
        });

        assertEquals(USER_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireId(id);
        verify(userRepository).existsById(id);
        verify(validationService).requireEntityExists(false, USER_NOT_FOUND_MSG);
        verify(userRepository, never()).findById(any());
    }

    @Test
    void addUser_valid_returnsSavedUser() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User result = userService.addUser(sampleUser);

        assertEquals(sampleUser, result);
        verify(validationService).requireNotNull(sampleUser, USER_CANNOT_BE_NULL_MSG);
        verify(userRepository).save(sampleUser);
    }

    @Test
    void addUser_null_throwsException() {
        doThrow(new IllegalArgumentException(USER_CANNOT_BE_NULL_MSG))
                .when(validationService).requireNotNull(null, USER_CANNOT_BE_NULL_MSG);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.addUser(null);
        });

        assertEquals(USER_CANNOT_BE_NULL_MSG, exception.getMessage());
        verify(validationService).requireNotNull(null, USER_CANNOT_BE_NULL_MSG);
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_valid_returnsUpdatedUser() {
        Long id = sampleUser.getId();
        when(userRepository.existsById(id)).thenReturn(true);
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User result = userService.updateUser(sampleUser);

        assertEquals(sampleUser, result);

        verify(validationService).requireNotNull(sampleUser, USER_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(id);
        verify(userRepository).existsById(id);
        verify(validationService).requireEntityExists(true, USER_NOT_FOUND_MSG);
        verify(userRepository).save(sampleUser);
    }

    @Test
    void updateUser_userNotFound_throwsException() {
        Long id = sampleUser.getId();
        when(userRepository.existsById(id)).thenReturn(false);

        doThrow(new IllegalStateException(USER_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, USER_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.updateUser(sampleUser);
        });

        assertEquals(USER_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireNotNull(sampleUser, USER_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(id);
        verify(userRepository).existsById(id);
        verify(validationService).requireEntityExists(false, USER_NOT_FOUND_MSG);
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser_existingUser_doesNotThrow() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> userService.deleteUser(id));

        verify(validationService).requireId(id);
        verify(userRepository).existsById(id);
        verify(validationService).requireEntityExists(true, USER_NOT_FOUND_MSG);
        verify(userRepository).deleteById(id);
    }

    @Test
    void deleteUser_userNotFound_throwsException() {
        Long id = 1L;
        when(userRepository.existsById(id)).thenReturn(false);

        doThrow(new IllegalStateException(USER_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, USER_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.deleteUser(id);
        });

        assertEquals(USER_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireId(id);
        verify(userRepository).existsById(id);
        verify(validationService).requireEntityExists(false, USER_NOT_FOUND_MSG);
        verify(userRepository, never()).deleteById(any());
    }
}*/