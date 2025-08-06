package org.example.adventure_planner.service;

import org.example.adventure_planner.model.GearItem;
import org.example.adventure_planner.repository.GearItemRepository;
import org.example.adventure_planner.repository.UserAdventureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GearItemServiceImpl implements GearItemService{
    private final GearItemRepository gearItemRepository;
    private final ValidationService validationService;
    private final UserAdventureRepository userAdventureRepository;

    public GearItemServiceImpl(GearItemRepository gearItemRepository,
                               ValidationService validationService,
                               UserAdventureRepository userAdventureRepository){
        this.gearItemRepository = gearItemRepository;
        this.validationService = validationService;
        this.userAdventureRepository = userAdventureRepository;
    }

    @Override
    public List<GearItem> getAllGearItems(){
        return gearItemRepository.findAll();
    }

    @Override
    public Optional<GearItem> getGearItemById(Long id){
        validationService.requireId(id);
        validationService.requireEntityExists(gearItemRepository.existsById(id),
                "Gear item with ID " + id + " not found");
        return gearItemRepository.findById(id);
    }

    @Override
    public GearItem addGearItem(GearItem gearItem){
        validationService.requireNotNull(gearItem, "Gear item cannot be null");
        return gearItemRepository.save(gearItem);
    }

    @Override
    public GearItem updateGearItem(GearItem gearItem){
        validationService.requireNotNull(gearItem, "Gear item cannot be null");
        validationService.requireId(gearItem.getId());
        validationService.requireEntityExists(gearItemRepository.existsById(gearItem.getId()),
                "Gear item not found");
        return gearItemRepository.save(gearItem);
    }

    @Override
    public void deleteGearItem(Long id){
        validationService.requireId(id);
        validationService.requireEntityExists(gearItemRepository.existsById(id),
                "Gear item not found");
        gearItemRepository.deleteById(id);
    }

    @Override
    public List<GearItem> getAllAdventureItems(Long adventureId){
        validationService.requireId(adventureId);
        validationService.requireEntityExists(userAdventureRepository.existsById(adventureId),
                "Adventure not found");
        return gearItemRepository.findByAdventureId(adventureId);
    }
}
