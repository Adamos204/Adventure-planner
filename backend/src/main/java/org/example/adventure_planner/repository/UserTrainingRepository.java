package org.example.adventure_planner.repository;

import org.example.adventure_planner.model.UserTraining;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserTrainingRepository extends JpaRepository<UserTraining, Long> {
    List<UserTraining> findByUserId(Long userId);
}

