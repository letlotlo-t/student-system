package com.example.ecommerce.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public String processPayment(double amount) {
        return "Payment of $" + amount + " processed successfully.";
    }
}