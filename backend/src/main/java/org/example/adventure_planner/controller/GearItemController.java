package org.example.adventure_planner.controller;

import org.example.adventure_planner.model.GearItem;
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
    public ResponseEntity<List<GearItem>> getAllGearItems() {
        return ResponseEntity.ok(gearItemService.getAllGearItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GearItem> getAdventureById(@PathVariable Long id){
        return gearItemService.getGearItemById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<GearItem> addGearItem(@RequestBody GearItem gearItem) {
        GearItem saveGearItem = gearItemService.addGearItem(gearItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveGearItem);
    }

    @PutMapping
    public ResponseEntity<GearItem> updateGearItem(@RequestBody GearItem gearItem) {
        return ResponseEntity.ok(gearItemService.updateGearItem(gearItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGearItem(@PathVariable Long id) {
        gearItemService.deleteGearItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/adventure/{adventureId}")
    public ResponseEntity<List<GearItem>> getAllAdventureItems(@PathVariable Long adventureId) {
        return ResponseEntity.ok(gearItemService.getAllAdventureItems(adventureId));
    }
}
