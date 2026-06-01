package com.example.ecommerce.filter;

import com.example.ecommerce.model.Role;
import com.example.ecommerce.model.UserSession;
import com.example.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RoleGuardFilter implements HandlerInterceptor {

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String token = request.getHeader("X-Session-Token");
        boolean allowed = authService.hasRole(token, Role.ADMIN, Role.LECTURER);

        if (!allowed) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"error\": \"Access denied. Admin or Lecturer role required.\"}"
            );
            return false;
        }

        return true;
    }
}