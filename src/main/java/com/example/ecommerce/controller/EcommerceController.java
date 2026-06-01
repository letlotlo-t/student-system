package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.*;
import com.example.ecommerce.service.UserService;
import com.example.ecommerce.service.PaymentService;

@RestController
@RequestMapping("/api")
public class EcommerceController {

    private final UserService userService;
    private final PaymentService paymentService;

    public EcommerceController(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return userService.authenticate(username, password);
    }

    @PostMapping("/pay")
    public String pay(@RequestParam double amount) {
        return paymentService.processPayment(amount);
    }

    // Add a simple GET endpoint for testing
    @GetMapping("/test")
    public String test() {
        return "E-commerce API is working!";
    }
}