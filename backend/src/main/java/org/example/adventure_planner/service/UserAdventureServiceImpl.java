package org.example.adventure_planner.service;

import org.example.adventure_planner.model.UserAdventure;
import org.example.adventure_planner.repository.UserAdventureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAdventureServiceImpl implements UserAdventureService{

    private final UserAdventureRepository userAdventureRepository;
    private final ValidationService validationService;

    public UserAdventureServiceImpl(UserAdventureRepository userAdventureRepository,
                                    ValidationService validationService) {
        this.userAdventureRepository = userAdventureRepository;
        this.validationService = validationService;
    }

    @Override
    public List<UserAdventure> getAllAdventures() {
        return userAdventureRepository.findAll();
    }

    @Override
    public Optional<UserAdventure> getAdventureById(Long id) {
        validationService.requireId(id);
        validationService.requireEntityExists(userAdventureRepository.existsById(id),
                "Adventure with ID " + id + " not found");
        return userAdventureRepository.findById(id);
    }

    @Override
    public UserAdventure addAdventure(UserAdventure userAdventure) {
        validationService.requireNotNull(userAdventure, "Adventure cannot be null");
        return userAdventureRepository.save(userAdventure);
    }

    @Override
    public UserAdventure updateAdventure(UserAdventure userAdventure) {
        validationService.requireNotNull(userAdventure, "Adventure cannot be null");
        validationService.requireId(userAdventure.getId());
        validationService.requireEntityExists(userAdventureRepository.existsById(userAdventure.getId()),
                "Adventure not found");
        return userAdventureRepository.save(userAdventure);
    }

    @Override
    public void deleteAdventure(Long id) {
        validationService.requireId(id);
        validationService.requireEntityExists(userAdventureRepository.existsById(id),
                "Adventure not found");
        userAdventureRepository.deleteById(id);
    }

    @Override
    public List<UserAdventure> getAllUsersAdventures(Long userId) {
        validationService.requireId(userId);
        return userAdventureRepository.findByUserId(userId);
    }
}
