package org.example.adventure_planner.service;

import org.example.adventure_planner.model.User;
import org.example.adventure_planner.repository.UserRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidationService validationService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ValidationService validationService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.validationService = validationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id){
        validationService.requireId(id);
        validationService.requireEntityExists(userRepository.existsById(id),
                "User not found");
        return userRepository.findById(id);
    }

    @Override
    public User addUser(User user){
        validationService.requireNotNull(user, "User cannot be null");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user){
        validationService.requireNotNull(user, "User cannot be null");
        validationService.requireId(user.getId());
        validationService.requireEntityExists(userRepository.existsById(user.getId()),
                "User not found");
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id){
        validationService.requireId(id);
        validationService.requireEntityExists(userRepository.existsById(id),
                "User not found");
        userRepository.deleteById(id);
    }
}

