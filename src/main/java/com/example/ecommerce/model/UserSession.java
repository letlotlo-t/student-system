package com.example.ecommerce.model;

public class UserSession {

    private String username;
    private String token;
    private Role role;

    public UserSession(String username, String token, Role role) {
        this.username = username;
        this.token    = token;
        this.role     = role;
    }

    public String getUsername() {
        return username;
    }
    public String getToken()    {
        return token;

    }
    public Role   getRole()     {
        return role;
    }
}