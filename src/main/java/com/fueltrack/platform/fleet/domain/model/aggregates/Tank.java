package com.fueltrack.platform.fleet.domain.model.aggregates;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tanks")
public class Tank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long providerId;

    @Column(nullable = false, length = 50)
    private String plate;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false)
    private Double capacityGallons;

    @Column(nullable = false)
    private Double currentFuelGallons;

    @Column(nullable = false, length = 50)
    private String status; // AVAILABLE, ON_ROUTE, MAINTENANCE

    @Column(nullable = false, length = 50)
    @Builder.Default
    private String smartLockStatus = "LOCKED"; // LOCKED, UNLOCKED

    @Column(nullable = false, length = 50)
    @Builder.Default
    private String valveStatus = "CLOSED"; // CLOSED, OPEN

    @Column(nullable = false)
    @Builder.Default
    private Double tirePressurePsi = 32.0;

    @Column(nullable = false)
    @Builder.Default
    private Double speedKmh = 0.0;
}
