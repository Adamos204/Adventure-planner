package org.example.adventure_planner.controller;

import org.example.adventure_planner.dto.GearItemRequestDTO;
import org.example.adventure_planner.dto.GearItemResponseDTO;
import org.example.adventure_planner.service.GearItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gear-items")
public class GearItemController {

    private final GearItemService gearItemService;

    public GearItemController(GearItemService gearItemService) {
        this.gearItemService = gearItemService;
    }

    @GetMapping
    public ResponseEntity<List<GearItemResponseDTO>> getAllGearItems() {
        List<GearItemResponseDTO> items = gearItemService.getAllGearItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GearItemResponseDTO> getGearItemById(@PathVariable Long id) {
        GearItemResponseDTO item = gearItemService.getGearItemById(id);
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<GearItemResponseDTO> addGearItem(@RequestBody GearItemRequestDTO dto) {
        GearItemResponseDTO saved = gearItemService.addGearItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GearItemResponseDTO> updateGearItem(
            @PathVariable Long id,
            @RequestBody GearItemRequestDTO dto) {
        GearItemResponseDTO updated = gearItemService.updateGearItem(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGearItem(@PathVariable Long id) {
        gearItemService.deleteGearItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/adventure/{adventureId}")
    public ResponseEntity<List<GearItemResponseDTO>> getAllAdventureItems(@PathVariable Long adventureId) {
        List<GearItemResponseDTO> items = gearItemService.getAllAdventureItems(adventureId);
        return ResponseEntity.ok(items);
    }
}
