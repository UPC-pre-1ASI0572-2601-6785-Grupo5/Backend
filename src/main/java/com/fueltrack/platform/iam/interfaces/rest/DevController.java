package com.fueltrack.platform.iam.interfaces.rest;

import com.fueltrack.platform.iam.domain.model.User;
import com.fueltrack.platform.iam.domain.services.UserRepository;
import com.fueltrack.platform.orderpayment.infrastructure.persistence.JpaOrderRepository;
import com.fueltrack.platform.orderpayment.domain.services.PaymentRepository;
import com.fueltrack.platform.inventory.domain.services.SiteRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dev")
public class DevController {

    private final UserRepository userRepository;
    private final JpaOrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final SiteRepository siteRepository;

    public DevController(UserRepository userRepository, JpaOrderRepository orderRepository, PaymentRepository paymentRepository, SiteRepository siteRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.siteRepository = siteRepository;
    }

    @DeleteMapping("/wipe")
    @Transactional
    public String wipe() {
        System.out.println("Executing manual DB wipe...");
        paymentRepository.deleteAll();
        orderRepository.deleteAll();
        siteRepository.deleteAll();
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (!u.getEmail().equals("123") && !u.getEmail().equals("321")) {
                userRepository.delete(u);
            }
        }
        return "Database wiped successfully except users 123 and 321";
    }
}
