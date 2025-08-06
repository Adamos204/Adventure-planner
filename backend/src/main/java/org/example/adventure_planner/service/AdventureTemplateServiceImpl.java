package org.example.adventure_planner.service;

import org.example.adventure_planner.model.AdventureTemplate;
import org.example.adventure_planner.repository.AdventureTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdventureTemplateServiceImpl implements AdventureTemplateService{
    private final AdventureTemplateRepository adventureTemplateRepository;
    private final ValidationService validationService;

    public AdventureTemplateServiceImpl(AdventureTemplateRepository adventureTemplateRepository, ValidationService validationService) {
        this.adventureTemplateRepository = adventureTemplateRepository;
        this.validationService = validationService;
    }

    @Override
    public List<AdventureTemplate> getAllAdventureTemplates() {
        return adventureTemplateRepository.findAll();
    }

    @Override
    public Optional<AdventureTemplate> getAdventureTemplateById(Long id) {
        validationService.requireId(id);
        validationService.requireEntityExists(adventureTemplateRepository.existsById(id),
                "Template with ID " + id + " not found");
        return adventureTemplateRepository.findById(id);
    }

    @Override
    public AdventureTemplate addAdventureTemplate(AdventureTemplate adventureTemplate) {
        validationService.requireNotNull(adventureTemplate, "Template cannot be null");
        return adventureTemplateRepository.save(adventureTemplate);
    }

    @Override
    public AdventureTemplate updateAdventureTemplate(AdventureTemplate adventureTemplate) {
        validationService.requireNotNull(adventureTemplate, "Template cannot be null");
        validationService.requireId(adventureTemplate.getId());
        validationService.requireEntityExists(adventureTemplateRepository.existsById(adventureTemplate.getId()),
                "Template not found");
        return adventureTemplateRepository.save(adventureTemplate);
    }

    @Override
    public void deleteAdventureTemplate(Long id) {
        validationService.requireId(id);
        validationService.requireEntityExists(adventureTemplateRepository.existsById(id),
                "Template not found");
        adventureTemplateRepository.deleteById(id);
    }
}
