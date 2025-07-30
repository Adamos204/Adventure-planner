package org.example.adventure_planner.repository;

import org.example.adventure_planner.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
