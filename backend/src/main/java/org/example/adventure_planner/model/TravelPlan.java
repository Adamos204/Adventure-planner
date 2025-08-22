package org.example.adventure_planner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Time;

@Data
@Entity
@Table(name = "travel_plan")
public class TravelPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false, length = 50)
    private String type;

    @NotBlank
    @Size(min = 2, max = 255)
    @Column(nullable = false)
    private String departureLocation;

    @NotBlank
    @Size(min = 2, max = 255)
    @Column(nullable = false)
    private String arrivalLocation;

    @NotNull
    @Column(name = "departure_time", nullable = false)
    private Time departureTime;

    @NotNull
    @Column(name = "arrival_time", nullable = false)
    private Time arrivalTime;

    @Size(max = 1000)
    @Column(length = 1000)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_adventure_id", nullable = false)
    private UserAdventure userAdventure;
}
