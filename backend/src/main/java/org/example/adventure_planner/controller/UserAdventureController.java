package org.example.adventure_planner.controller;

import org.example.adventure_planner.dto.UserAdventureRequestDTO;
import org.example.adventure_planner.dto.UserAdventureResponseDTO;
import org.example.adventure_planner.service.UserAdventureService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-adventure")
public class UserAdventureController {

    private final UserAdventureService userAdventureService;

    public UserAdventureController(UserAdventureService userAdventureService) {
        this.userAdventureService = userAdventureService;
    }

    @GetMapping
    public ResponseEntity<List<UserAdventureResponseDTO>> getAllAdventures() {
        return ResponseEntity.ok(userAdventureService.getAllAdventures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAdventureResponseDTO> getAdventure(@PathVariable Long id) {
        return ResponseEntity.ok(userAdventureService.getAdventureById(id));
    }

    @PostMapping
    public ResponseEntity<UserAdventureResponseDTO> addAdventure(@RequestBody UserAdventureRequestDTO dto, Authentication authentication) {
        return ResponseEntity.ok(userAdventureService.addAdventure(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAdventureResponseDTO> updateAdventure(@PathVariable Long id,
                                                                    @RequestBody UserAdventureRequestDTO dto) {
        return ResponseEntity.ok(userAdventureService.updateAdventure(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdventure(@PathVariable Long id) {
        userAdventureService.deleteAdventure(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAdventureResponseDTO>> getAllUsersAdventures(@PathVariable Long userId) {
        return ResponseEntity.ok(userAdventureService.getAllUsersAdventures(userId));
    }
}
