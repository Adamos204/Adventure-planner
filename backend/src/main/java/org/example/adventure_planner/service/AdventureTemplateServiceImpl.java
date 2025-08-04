package org.example.adventure_planner.service;

import org.example.adventure_planner.model.AdventureTemplate;
import org.example.adventure_planner.repository.AdventureTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdventureTemplateServiceImpl implements AdventureTemplateService{
    private final AdventureTemplateRepository adventureTemplateRepository;

    public AdventureTemplateServiceImpl(AdventureTemplateRepository adventureTemplateRepository){
        this.adventureTemplateRepository = adventureTemplateRepository;
    }

    private void requireId(Long id) {
        if (id == null) throw new IllegalArgumentException("ID cannot be null");
    }

    private void requireTemplateExists(Long id) {
        if (!adventureTemplateRepository.existsById(id)) {
            throw new RuntimeException("Template with ID " + id + " not found");
        }
    }

    private void requireTemplateNotNull(AdventureTemplate adventureTemplate) {
        if (adventureTemplate == null) throw new IllegalArgumentException("Template cannot be null");
    }

    @Override
    public List<AdventureTemplate> getAllAdventureTemplates(){
        return adventureTemplateRepository.findAll();
    }

    @Override
    public Optional<AdventureTemplate> getAdventureTemplateById(Long id){
        requireId(id);
        requireTemplateExists(id);
        return adventureTemplateRepository.findById(id);
    }

    @Override
    public AdventureTemplate addAdventureTemplate(AdventureTemplate adventureTemplate){
        requireTemplateNotNull(adventureTemplate);
        return adventureTemplateRepository.save(adventureTemplate);
    }

    @Override
    public AdventureTemplate updateAdventureTemplate(AdventureTemplate adventureTemplate){
        requireTemplateNotNull(adventureTemplate);
        requireId(adventureTemplate.getId());
        requireTemplateExists(adventureTemplate.getId());
        return adventureTemplateRepository.save(adventureTemplate);
    }

    @Override
    public void deleteAdventureTemplate(Long id){
        requireId(id);
        requireTemplateExists(id);
        adventureTemplateRepository.deleteById(id);
    }

}
