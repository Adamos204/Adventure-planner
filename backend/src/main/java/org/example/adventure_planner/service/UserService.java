package org.example.adventure_planner.service;

import org.example.adventure_planner.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
}