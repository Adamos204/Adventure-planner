package org.example.adventure_planner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_adventure")
public class UserAdventure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String location;

    @NotBlank
    @Size(min = 20, max = 300)
    @Column(nullable = false, length = 300)
    private String description;

    @FutureOrPresent(message = "The adventure date must be today or in the future")
    private LocalDate date;

    @Size(max = 500)
    private String notes;

    @NotNull
    @PositiveOrZero
    @Column(name = "duration_days", nullable = false)
    private int lengthInDays;

    @NotNull
    @PositiveOrZero
    @Column(name = "distance_km", nullable = false)
    private Double lengthInKm;

    @NotBlank
    @Size(min = 10, max = 50)
    @Column(nullable = false, length = 50, name = "start_location")
    private String startLocation;

    @NotBlank
    @Size(min = 10, max = 50)
    @Column(nullable = false, length = 50, name = "end_location")
    private String endLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
