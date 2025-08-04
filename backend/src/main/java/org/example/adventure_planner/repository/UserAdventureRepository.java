package org.example.adventure_planner.repository;

import org.example.adventure_planner.model.UserAdventure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAdventureRepository extends JpaRepository<UserAdventure, Long> {
    List<UserAdventure> findByUserId(Long id);
}
