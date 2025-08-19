package org.example.adventure_planner.service;

import org.example.adventure_planner.dto.UserAdventureRequestDTO;
import org.example.adventure_planner.dto.UserAdventureResponseDTO;

import java.util.List;

public interface UserAdventureService {
    List<UserAdventureResponseDTO> getAllAdventures();
    UserAdventureResponseDTO getAdventureById(Long id);
    UserAdventureResponseDTO addAdventure(UserAdventureRequestDTO dto);
    UserAdventureResponseDTO updateAdventure(Long id, UserAdventureRequestDTO dto);
    void deleteAdventure(Long id);
    List<UserAdventureResponseDTO> getAllUsersAdventures(Long userId);
}
