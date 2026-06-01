package com.example.ecommerce.service;

import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.UserSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    // Token-based store
    private static final Map<String, UserSession> TOKEN_STORE = new HashMap<>();

    // Username/password store
    private static final Map<String, String[]> CREDENTIALS = new HashMap<>();

    static {
        TOKEN_STORE.put("student-token-2025",
                new UserSession("letlotlo", "student-token-2025", Role.STUDENT));
        TOKEN_STORE.put("lecturer-token-2025",
                new UserSession("dr.smith", "lecturer-token-2025", Role.LECTURER));
        TOKEN_STORE.put("admin-token-2025",
                new UserSession("admin", "admin-token-2025", Role.ADMIN));

        // username -> [password, token]
        CREDENTIALS.put("letlotlo",  new String[]{"student123",  "student-token-2025"});
        CREDENTIALS.put("dr.smith",  new String[]{"lecturer123", "lecturer-token-2025"});
        CREDENTIALS.put("admin",     new String[]{"admin123",    "admin-token-2025"});
    }

    public UserSession getUserByToken(String token) {
        return TOKEN_STORE.get(token);
    }

    public UserSession authenticate(String username, String password) {
        String[] creds = CREDENTIALS.get(username);
        if (creds == null || !creds[0].equals(password)) return null;
        return TOKEN_STORE.get(creds[1]);
    }

    public boolean hasRole(String token, Role... allowedRoles) {
        UserSession user = getUserByToken(token);
        if (user == null) return false;
        for (Role role : allowedRoles) {
            if (user.getRole() == role) return true;
        }
        return false;
    }
}