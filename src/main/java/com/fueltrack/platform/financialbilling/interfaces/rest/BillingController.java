package com.fueltrack.platform.financialbilling.interfaces.rest;

import com.fueltrack.platform.financialbilling.domain.model.Invoice;
import com.fueltrack.platform.financialbilling.domain.services.InvoiceRepository;
import com.fueltrack.platform.iam.domain.model.User;
import com.fueltrack.platform.iam.domain.services.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/billing")
public class BillingController {

    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;

    public BillingController(InvoiceRepository invoiceRepository, UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/users/{userId}/invoices")
    public ResponseEntity<List<Invoice>> getUserInvoices(@PathVariable Long userId) {
        return ResponseEntity.ok(invoiceRepository.findByUserIdOrderByIssueDateDesc(userId));
    }

    @PutMapping("/users/{userId}/plan")
    public ResponseEntity<?> changePlan(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        String newPlan = request.get("plan");
        if (newPlan == null || newPlan.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Plan name is required");
        }

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setSubscriptionPlan(newPlan);
            userRepository.save(user);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
