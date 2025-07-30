package org.example.adventure_planner.service;

import org.example.adventure_planner.model.User;
import org.example.adventure_planner.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    private void requireId(Long id) {
        if (id == null) throw new IllegalArgumentException("ID cannot be null");
    }

    private void requireUserExists(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }

    private void requireUserNotNull(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id){
        requireId(id);
        requireUserExists(id);
        return userRepository.findById(id);
    }

    @Override
    public User addUser(User user){
        requireUserNotNull(user);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user){
        requireUserNotNull(user);
        requireId(user.getId());
        requireUserExists(user.getId());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id){
        requireId(id);
        requireUserExists(id);
        userRepository.deleteById(id);
    }
}

