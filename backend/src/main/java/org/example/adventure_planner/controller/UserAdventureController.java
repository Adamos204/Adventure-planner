package org.example.adventure_planner.controller;


import org.example.adventure_planner.model.UserAdventure;
import org.example.adventure_planner.service.UserAdventureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-adventure")
public class UserAdventureController {

    private final UserAdventureService userAdventureService;

    public UserAdventureController(UserAdventureService userAdventureService){
        this.userAdventureService = userAdventureService;
    }

    @GetMapping
    public ResponseEntity<List<UserAdventure>> getAllAdventures(){
        return ResponseEntity.ok(userAdventureService.getAllAdventures());
    }

    @PostMapping
    public ResponseEntity<UserAdventure> addAdventure(@RequestBody UserAdventure userAdventure) {
        UserAdventure saved = userAdventureService.addAdventure(userAdventure);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAdventure> getAdventureById(@PathVariable Long id){
        return userAdventureService.getAdventureById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping
    public ResponseEntity<UserAdventure> updateAdventure(@RequestBody UserAdventure userAdventure) {
        return ResponseEntity.ok(userAdventureService.updateAdventure(userAdventure));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdventure(@PathVariable Long id) {
        userAdventureService.deleteAdventure(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserAdventure>> getAllUsersAdventures(@PathVariable Long userId) {
        return ResponseEntity.ok(userAdventureService.getAllUsersAdventures(userId));
    }
}
