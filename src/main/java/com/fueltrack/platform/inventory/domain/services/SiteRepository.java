package com.fueltrack.platform.inventory.domain.services;

import com.fueltrack.platform.inventory.domain.model.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    List<Site> findByUserId(Long userId);
}
