package com.example.ecommerce.service;

import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionService {

    private static final String CART_KEY = "studentCart";

    public void addToCart(HttpSession session, String courseCode) {
        List<String> cart = getCart(session);
        if (!cart.contains(courseCode)) {
            cart.add(courseCode);
        }
        session.setAttribute(CART_KEY, cart);
    }

    @SuppressWarnings("unchecked")
    public List<String> getCart(HttpSession session) {
        List<String> cart = (List<String>) session.getAttribute(CART_KEY);
        return cart != null ? cart : new ArrayList<>();
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_KEY);
    }
}