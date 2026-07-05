package com.fueltrack.platform.fleet.interfaces.rest;

import com.fueltrack.platform.fleet.application.internal.FleetCommandService;
import com.fueltrack.platform.fleet.domain.model.aggregates.Driver;
import com.fueltrack.platform.fleet.domain.model.aggregates.Tank;
import com.fueltrack.platform.fleet.interfaces.rest.requests.DriverRequest;
import com.fueltrack.platform.fleet.interfaces.rest.requests.TankRequest;
import com.fueltrack.platform.iam.domain.model.User;
import com.fueltrack.platform.iam.domain.model.UserRole;
import com.fueltrack.platform.iam.domain.services.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Tag(name = "Fleet", description = "Fleet Management (Drivers and Tanks)")
@RestController
@RequestMapping("/api/v1")
public class FleetController {

    private final FleetCommandService fleetCommandService;
    private final UserRepository userRepository;

    public FleetController(FleetCommandService fleetCommandService, UserRepository userRepository) {
        this.fleetCommandService = fleetCommandService;
        this.userRepository = userRepository;
    }

    private User resolveUser(UserDetails currentUser) {
        return userRepository.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private void verifyProvider(User user) {
        if (user.getRole() != UserRole.PROVIDER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only providers can access fleet endpoints");
        }
    }

    // --- DRIVERS ---

    @Operation(summary = "List all drivers for the authenticated provider")
    @GetMapping("/drivers")
    public List<Driver> listDrivers(@AuthenticationPrincipal UserDetails currentUser) {
        User user = resolveUser(currentUser);
        verifyProvider(user);
        return fleetCommandService.listDrivers(user.getId());
    }

    @Operation(summary = "Add a new driver")
    @PostMapping("/drivers")
    @ResponseStatus(HttpStatus.CREATED)
    public Driver addDriver(@AuthenticationPrincipal UserDetails currentUser, @Valid @RequestBody DriverRequest request) {
        User user = resolveUser(currentUser);
        verifyProvider(user);

        Driver driver = Driver.builder()
                .providerId(user.getId())
                .name(request.getName())
                .licenseNumber(request.getLicenseNumber())
                .profilePicture(request.getProfilePicture())
                .status(request.getStatus())
                .build();
        return fleetCommandService.saveDriver(driver);
    }

    @Operation(summary = "Update an existing driver")
    @PutMapping("/drivers/{id}")
    public Driver updateDriver(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long id, @Valid @RequestBody DriverRequest request) {
        User user = resolveUser(currentUser);
        verifyProvider(user);

        List<Driver> existingDrivers = fleetCommandService.listDrivers(user.getId());
        Driver driverToUpdate = existingDrivers.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Driver not found"));

        driverToUpdate.setName(request.getName());
        driverToUpdate.setLicenseNumber(request.getLicenseNumber());
        driverToUpdate.setProfilePicture(request.getProfilePicture());
        driverToUpdate.setStatus(request.getStatus());

        return fleetCommandService.saveDriver(driverToUpdate);
    }

    @Operation(summary = "Delete a driver")
    @DeleteMapping("/drivers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDriver(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long id) {
        User user = resolveUser(currentUser);
        verifyProvider(user);
        fleetCommandService.deleteDriver(id, user.getId());
    }

    // --- TANKS ---

    @Operation(summary = "List all tanks for the authenticated provider")
    @GetMapping("/tanks")
    public List<Tank> listTanks(@AuthenticationPrincipal UserDetails currentUser) {
        User user = resolveUser(currentUser);
        verifyProvider(user);
        return fleetCommandService.listTanks(user.getId());
    }

    @Operation(summary = "Add a new tank")
    @PostMapping("/tanks")
    @ResponseStatus(HttpStatus.CREATED)
    public Tank addTank(@AuthenticationPrincipal UserDetails currentUser, @Valid @RequestBody TankRequest request) {
        User user = resolveUser(currentUser);
        verifyProvider(user);

        Tank tank = Tank.builder()
                .providerId(user.getId())
                .plate(request.getPlate())
                .model(request.getModel())
                .capacityGallons(request.getCapacityGallons())
                .currentFuelGallons(request.getCurrentFuelGallons())
                .status(request.getStatus())
                .build();
        return fleetCommandService.saveTank(tank);
    }

    @Operation(summary = "Update an existing tank")
    @PutMapping("/tanks/{id}")
    public Tank updateTank(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long id, @Valid @RequestBody TankRequest request) {
        User user = resolveUser(currentUser);
        verifyProvider(user);

        List<Tank> existingTanks = fleetCommandService.listTanks(user.getId());
        Tank tankToUpdate = existingTanks.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tank not found"));

        tankToUpdate.setPlate(request.getPlate());
        tankToUpdate.setModel(request.getModel());
        tankToUpdate.setCapacityGallons(request.getCapacityGallons());
        tankToUpdate.setCurrentFuelGallons(request.getCurrentFuelGallons());
        tankToUpdate.setStatus(request.getStatus());

        return fleetCommandService.saveTank(tankToUpdate);
    }

    @Operation(summary = "Delete a tank")
    @DeleteMapping("/tanks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTank(@AuthenticationPrincipal UserDetails currentUser, @PathVariable Long id) {
        User user = resolveUser(currentUser);
        verifyProvider(user);
        fleetCommandService.deleteTank(id, user.getId());
    }
}
