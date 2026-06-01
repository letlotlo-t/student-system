package com.example.ecommerce.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String authenticate(String username, String password) {
        return "User authenticated: " + username;
    }
}