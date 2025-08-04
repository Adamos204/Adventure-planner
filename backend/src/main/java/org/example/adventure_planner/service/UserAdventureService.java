package org.example.adventure_planner.service;

import org.example.adventure_planner.model.UserAdventure;

import java.util.List;
import java.util.Optional;

public interface UserAdventureService {
    List<UserAdventure> getAllAdventures();
    Optional<UserAdventure> getAdventureById(Long id);
    UserAdventure addAdventure(UserAdventure userAdventure);
    UserAdventure updateAdventure(UserAdventure userAdventure);
    void deleteAdventure(Long id);
    List<UserAdventure> getAllUsersAdventures(Long userId);
}
