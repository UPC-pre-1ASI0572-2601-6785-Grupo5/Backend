package com.fueltrack.platform.fleet.application.internal;

import com.fueltrack.platform.fleet.domain.model.aggregates.Driver;
import com.fueltrack.platform.fleet.domain.model.aggregates.Tank;
import com.fueltrack.platform.fleet.infrastructure.persistence.jpa.repositories.DriverRepository;
import com.fueltrack.platform.fleet.infrastructure.persistence.jpa.repositories.TankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FleetCommandService {

    private final DriverRepository driverRepository;
    private final TankRepository tankRepository;

    public FleetCommandService(DriverRepository driverRepository, TankRepository tankRepository) {
        this.driverRepository = driverRepository;
        this.tankRepository = tankRepository;
    }

    @Transactional(readOnly = true)
    public List<Driver> listDrivers(Long providerId) {
        return driverRepository.findByProviderId(providerId);
    }

    @Transactional
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Transactional
    public void deleteDriver(Long id, Long providerId) {
        driverRepository.findById(id).ifPresent(driver -> {
            if (driver.getProviderId().equals(providerId)) {
                driverRepository.delete(driver);
            }
        });
    }

    @Transactional(readOnly = true)
    public List<Tank> listTanks(Long providerId) {
        return tankRepository.findByProviderId(providerId);
    }

    @Transactional
    public Tank saveTank(Tank tank) {
        return tankRepository.save(tank);
    }

    @Transactional
    public void deleteTank(Long id, Long providerId) {
        tankRepository.findById(id).ifPresent(tank -> {
            if (tank.getProviderId().equals(providerId)) {
                tankRepository.delete(tank);
            }
        });
    }
}
