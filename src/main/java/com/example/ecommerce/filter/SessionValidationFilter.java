package com.example.ecommerce.filter;

import com.example.ecommerce.model.UserSession;
import com.example.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SessionValidationFilter implements HandlerInterceptor {

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // Check HTTP session first (browser login)
        UserSession sessionUser = (UserSession) request.getSession().getAttribute("currentUser");
        if (sessionUser != null) {
            request.setAttribute("currentUser", sessionUser);
            return true;
        }

        // Fall back to header token (Postman / API)
        String token = request.getHeader("X-Session-Token");
        if (token != null && !token.isEmpty()) {
            UserSession user = authService.getUserByToken(token);
            if (user != null) {
                request.setAttribute("currentUser", user);
                return true;
            }
        }

        // Neither session nor token — redirect to login for browser, 401 for API
        String acceptHeader = request.getHeader("Accept");
        if (acceptHeader != null && acceptHeader.contains("text/html")) {
            response.sendRedirect("/auth/login");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Missing or invalid session token.\"}");
        }
        return false;
    }
}