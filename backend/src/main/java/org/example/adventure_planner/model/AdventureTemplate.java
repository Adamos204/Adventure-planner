package org.example.adventure_planner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "adventure_template")
@Data
public class AdventureTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 255)
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank
    @Size(min = 5, max = 255)
    @Column(nullable = false)
    private String location;

    @NotBlank
    @Size(min = 50, max = 1000)
    @Column(nullable = false, length = 200)
    private String description;

    @NotNull
    @PositiveOrZero
    @Column(name = "distance_days", nullable = false)
    private int lengthInDays;

    @NotNull
    @PositiveOrZero
    @Column(name = "distance_km", nullable = false)
    private int lengthInKm;

    @NotBlank
    @Size(min = 10, max = 100)
    @Column(nullable = false, length = 50, name = "start_location")
    private String startLocation;

    @NotBlank
    @Size(min = 10, max = 100)
    @Column(nullable = false, length = 50, name = "end_location")
    private String endLocation;

}

