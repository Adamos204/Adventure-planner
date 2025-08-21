package org.example.adventure_planner.dto;

public record UserDTO(
        Long id,
        String firstname,
        String surname,
        String username,
        String email
) {}
