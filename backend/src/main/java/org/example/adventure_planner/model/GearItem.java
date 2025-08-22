package org.example.adventure_planner.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ManyToAny;

@Data
@Entity
@Table(name = "gear_items")
public class GearItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 20)
    @Column(nullable = false, length = 20)
    private String name;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean packed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adventure_id", nullable = false)
    private UserAdventure adventure;
}
