package org.example.adventure_planner.service;

import org.example.adventure_planner.model.AdventureTemplate;

import java.util.List;
import java.util.Optional;

public interface AdventureTemplateService {
    List<AdventureTemplate> getAllAdventureTemplates();
    Optional<AdventureTemplate> getAdventureTemplateById(Long id);
    AdventureTemplate addAdventureTemplate(AdventureTemplate adventureTemplate);
    AdventureTemplate updateAdventureTemplate(AdventureTemplate adventureTemplate);
    void deleteAdventureTemplate(Long id);
}
