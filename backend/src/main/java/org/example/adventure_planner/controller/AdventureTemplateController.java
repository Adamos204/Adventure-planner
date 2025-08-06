package  org.example.adventure_planner.controller;

import org.example.adventure_planner.model.AdventureTemplate;
import org.example.adventure_planner.model.GearItem;
import org.example.adventure_planner.service.AdventureTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adventure-template")
public class AdventureTemplateController {

    private final AdventureTemplateService adventureTemplateService;

    public AdventureTemplateController(AdventureTemplateService adventureTemplateService){
        this.adventureTemplateService = adventureTemplateService;
    }

    @GetMapping
    public ResponseEntity<List<AdventureTemplate>> getAllAdventureTemplates(){
        return ResponseEntity.ok(adventureTemplateService.getAllAdventureTemplates());
    }

    @PostMapping
    public ResponseEntity<AdventureTemplate> addAdventureTemplate(@RequestBody AdventureTemplate adventureTemplate){
        AdventureTemplate savedTemplate = adventureTemplateService.addAdventureTemplate(adventureTemplate);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTemplate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdventureTemplate> getAdventureTemplateById(@PathVariable Long id){
        AdventureTemplate adventureTemplate = adventureTemplateService.getAdventureTemplateById(id).orElseThrow(() ->
                new RuntimeException("Adventure template with ID " + id + " not found"));
        return ResponseEntity.ok(adventureTemplate);
    }

    @PutMapping
    public ResponseEntity<AdventureTemplate> updateAdventureTemplate(@RequestBody AdventureTemplate adventureTemplate){
        AdventureTemplate updatedTemplate = adventureTemplateService.updateAdventureTemplate(adventureTemplate);
        return ResponseEntity.ok(updatedTemplate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdventureTemplate(@PathVariable Long id){
        adventureTemplateService.deleteAdventureTemplate(id);
        return ResponseEntity.noContent().build();
    }
}
