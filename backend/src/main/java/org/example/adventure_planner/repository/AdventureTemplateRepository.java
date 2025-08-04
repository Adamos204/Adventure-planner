package org.example.adventure_planner.repository;

import org.example.adventure_planner.model.AdventureTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdventureTemplateRepository extends JpaRepository<AdventureTemplate, Long> {

}
