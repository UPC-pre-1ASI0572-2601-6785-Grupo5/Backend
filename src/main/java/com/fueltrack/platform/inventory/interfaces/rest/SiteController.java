package com.fueltrack.platform.inventory.interfaces.rest;

import com.fueltrack.platform.inventory.domain.model.Site;
import com.fueltrack.platform.inventory.domain.services.SiteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sites")
public class SiteController {

    private final SiteRepository siteRepository;

    public SiteController(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @PostMapping
    public ResponseEntity<Site> createSite(@RequestBody Site site) {
        Site savedSite = siteRepository.save(site);
        return ResponseEntity.ok(savedSite);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Site>> getUserSites(@PathVariable Long userId) {
        return ResponseEntity.ok(siteRepository.findByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Site> updateSite(@PathVariable Long id, @RequestBody Site siteDetails) {
        return siteRepository.findById(id).map(site -> {
            site.setName(siteDetails.getName());
            site.setAddress(siteDetails.getAddress());
            site.setActive(siteDetails.isActive());
            return ResponseEntity.ok(siteRepository.save(site));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSite(@PathVariable Long id) {
        return siteRepository.findById(id).map(site -> {
            siteRepository.delete(site);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
