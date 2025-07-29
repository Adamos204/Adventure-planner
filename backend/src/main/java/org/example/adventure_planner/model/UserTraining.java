package org.example.adventure_planner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_training")
public class UserTraining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    @NotBlank
    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String type;

    @NotNull
    @PositiveOrZero
    @Column(name = "duration_min", nullable = false)
    private Integer durationInMin;

    @NotNull
    @PositiveOrZero
    @Column(name = "distance_km", nullable = false)
    private Integer durationInKm;

    @Size(min = 3, max = 100)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
