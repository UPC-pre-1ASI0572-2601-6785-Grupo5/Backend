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
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long providerId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 100)
    private String licenseNumber;

    @Column(columnDefinition = "TEXT")
    private String profilePicture;

    @Column(nullable = false, length = 50)
    private String status; // AVAILABLE, ON_ROUTE, RESTING, FATIGUE

    @Column(nullable = false)
    @Builder.Default
    private Integer completedTripsSinceRest = 0;

    @Column
    private java.time.OffsetDateTime restingUntil;
}
