package org.example.adventure_planner.service;

import org.example.adventure_planner.dto.GearItemRequestDTO;
import org.example.adventure_planner.dto.GearItemResponseDTO;
import org.example.adventure_planner.exception.ResourceNotFoundException;
import org.example.adventure_planner.model.GearItem;
import org.example.adventure_planner.model.UserAdventure;
import org.example.adventure_planner.repository.GearItemRepository;
import org.example.adventure_planner.repository.UserAdventureRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GearItemServiceImpl implements GearItemService {

    private final GearItemRepository gearItemRepository;
    private final UserAdventureRepository userAdventureRepository;
    private final ValidationService validationService;

    public GearItemServiceImpl(GearItemRepository gearItemRepository,
                               UserAdventureRepository userAdventureRepository,
                               ValidationService validationService) {
        this.gearItemRepository = gearItemRepository;
        this.userAdventureRepository = userAdventureRepository;
        this.validationService = validationService;
    }

    private GearItemResponseDTO toResponseDto(GearItem item) {
        GearItemResponseDTO dto = new GearItemResponseDTO();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setQuantity(item.getQuantity());
        dto.setPacked(item.isPacked());
        dto.setAdventureId(item.getAdventure() != null ? item.getAdventure().getId() : null);
        return dto;
    }

    private void mapRequestToEntity(GearItemRequestDTO dto, GearItem item) {
        item.setName(dto.getName());
        item.setQuantity(dto.getQuantity());
        item.setPacked(dto.isPacked());

        if (dto.getAdventureId() != null) {
            UserAdventure adventure = new UserAdventure();
            adventure.setId(dto.getAdventureId());
            item.setAdventure(adventure);
        }
    }

    @Override
    public List<GearItemResponseDTO> getAllGearItems() {
        return gearItemRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public GearItemResponseDTO getGearItemById(Long id) {
        validationService.requireId(id);
        GearItem item = gearItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gear item not found"));
        return toResponseDto(item);
    }

    @Override
    public GearItemResponseDTO addGearItem(GearItemRequestDTO dto) {
        validationService.requireNotNull(dto, "Gear item cannot be null");
        GearItem item = new GearItem();
        mapRequestToEntity(dto, item);
        GearItem saved = gearItemRepository.save(item);
        return toResponseDto(saved);
    }

    @Override
    public GearItemResponseDTO updateGearItem(Long id, GearItemRequestDTO dto) {
        validationService.requireId(id);
        GearItem item = gearItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gear item not found"));
        mapRequestToEntity(dto, item);
        GearItem saved = gearItemRepository.save(item);
        return toResponseDto(saved);
    }

    @Override
    public void deleteGearItem(Long id) {
        validationService.requireId(id);
        if (!gearItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Gear item not found");
        }
        gearItemRepository.deleteById(id);
    }

    @Override
    public List<GearItemResponseDTO> getAllAdventureItems(Long adventureId) {
        validationService.requireId(adventureId);
        return gearItemRepository.findByAdventure_Id(adventureId)
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
