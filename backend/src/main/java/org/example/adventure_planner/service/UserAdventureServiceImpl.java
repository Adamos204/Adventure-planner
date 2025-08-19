package org.example.adventure_planner.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.adventure_planner.dto.UserAdventureRequestDTO;
import org.example.adventure_planner.dto.UserAdventureResponseDTO;
import org.example.adventure_planner.exception.ResourceNotFoundException;
import org.example.adventure_planner.model.User;
import org.example.adventure_planner.model.UserAdventure;
import org.example.adventure_planner.repository.UserAdventureRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAdventureServiceImpl implements UserAdventureService {

    private final UserAdventureRepository userAdventureRepository;
    private final ValidationService validationService;

    public UserAdventureServiceImpl(UserAdventureRepository userAdventureRepository,
                                    ValidationService validationService) {
        this.userAdventureRepository = userAdventureRepository;
        this.validationService = validationService;
    }

    private UserAdventureResponseDTO toResponseDTO(UserAdventure adventure) {
        UserAdventureResponseDTO dto = new UserAdventureResponseDTO();
        dto.setId(adventure.getId());
        dto.setName(adventure.getName());
        dto.setLocation(adventure.getLocation());
        dto.setDescription(adventure.getDescription());
        dto.setDate(adventure.getDate());
        dto.setNotes(adventure.getNotes());
        dto.setLengthInDays(adventure.getLengthInDays());
        dto.setLengthInKm(adventure.getLengthInKm());
        dto.setStartLocation(adventure.getStartLocation());
        dto.setEndLocation(adventure.getEndLocation());
        dto.setUserId(adventure.getUser() != null ? adventure.getUser().getId() : null);
        return dto;
    }

    private void mapRequestToEntity(UserAdventureRequestDTO dto, UserAdventure adventure) {
        adventure.setName(dto.getName());
        adventure.setLocation(dto.getLocation());
        adventure.setDescription(dto.getDescription());
        adventure.setDate(dto.getDate());
        adventure.setNotes(dto.getNotes());
        adventure.setLengthInDays(dto.getLengthInDays());
        adventure.setLengthInKm(dto.getLengthInKm());
        adventure.setStartLocation(dto.getStartLocation());
        adventure.setEndLocation(dto.getEndLocation());

        if (dto.getUserId() != null) {
            User user = new User();
            user.setId(dto.getUserId());
            adventure.setUser(user);
        }
    }

    @Override
    public List<UserAdventureResponseDTO> getAllAdventures() {
        return userAdventureRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserAdventureResponseDTO getAdventureById(Long id) {
        validationService.requireId(id);
        UserAdventure adventure = userAdventureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adventure not found"));
        return toResponseDTO(adventure);
    }

    @Override
    public UserAdventureResponseDTO addAdventure(UserAdventureRequestDTO dto) {
        validationService.requireNotNull(dto, "Adventure cannot be null");
        UserAdventure adventure = new UserAdventure();
        mapRequestToEntity(dto, adventure);
        UserAdventure saved = userAdventureRepository.save(adventure);
        return toResponseDTO(saved);
    }

    @Override
    public UserAdventureResponseDTO updateAdventure(Long id, UserAdventureRequestDTO dto) {
        validationService.requireId(id);
        UserAdventure adventure = userAdventureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Adventure not found"));
        mapRequestToEntity(dto, adventure);
        UserAdventure saved = userAdventureRepository.save(adventure);
        return toResponseDTO(saved);
    }

    @Override
    public void deleteAdventure(Long id) {
        validationService.requireId(id);
        if (!userAdventureRepository.existsById(id)) {
            throw new ResourceNotFoundException("Adventure not found");
        }
        userAdventureRepository.deleteById(id);
    }

    @Override
    public List<UserAdventureResponseDTO> getAllUsersAdventures(Long userId) {
        validationService.requireId(userId);
        return userAdventureRepository.findByUser_Id(userId).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}
