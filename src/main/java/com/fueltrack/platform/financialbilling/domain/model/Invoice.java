package com.fueltrack.platform.financialbilling.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime issueDate;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(length = 500)
    private String pdfUrl;

    @Column(length = 50)
    private String planName;
}
