package org.example.adventure_planner.service;

import org.example.adventure_planner.model.UserAdventure;
import org.example.adventure_planner.repository.UserAdventureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAdventureServiceImpl implements UserAdventureService{

    private final UserAdventureRepository userAdventureRepository;

    public UserAdventureServiceImpl(UserAdventureRepository userAdventureRepository){
        this.userAdventureRepository = userAdventureRepository;
    }

    private void requireId(Long id) {
        if (id == null) throw new IllegalArgumentException("ID cannot be null");
    }

    private void requireAdventureExists(Long id) {
        if (!userAdventureRepository.existsById(id)) {
            throw new RuntimeException("Template with ID " + id + " not found");
        }
    }

    private void requireAdventureNotNull(UserAdventure userAdventure) {
        if (userAdventure == null) throw new IllegalArgumentException("Template cannot be null");
    }

    @Override
    public List<UserAdventure> getAllAdventures(){
        return userAdventureRepository.findAll();
    }

    @Override
    public Optional<UserAdventure> getAdventureById(Long id){
        requireId(id);
        requireAdventureExists(id);
        return userAdventureRepository.findById(id);
    }

    @Override
    public UserAdventure addAdventure(UserAdventure userAdventure){
        requireAdventureNotNull(userAdventure);
        return userAdventureRepository.save(userAdventure);
    }

    @Override
    public UserAdventure updateAdventure(UserAdventure userAdventure){
        requireAdventureNotNull(userAdventure);
        requireId(userAdventure.getId());
        requireAdventureExists(userAdventure.getId());
        return userAdventureRepository.save(userAdventure);
    }

    @Override
    public void deleteAdventure(Long id){
        requireId(id);
        requireAdventureExists(id);
        userAdventureRepository.deleteById(id);
    }

    @Override
    public List<UserAdventure> getAllUsersAdventures(Long userId){
        requireId(userId);
        return userAdventureRepository.findByUserId(userId);
    }
}
