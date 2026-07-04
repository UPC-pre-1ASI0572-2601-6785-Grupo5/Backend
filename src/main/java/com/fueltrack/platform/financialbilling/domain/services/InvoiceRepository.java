package com.fueltrack.platform.financialbilling.domain.services;

import com.fueltrack.platform.financialbilling.domain.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByUserIdOrderByIssueDateDesc(Long userId);
}
