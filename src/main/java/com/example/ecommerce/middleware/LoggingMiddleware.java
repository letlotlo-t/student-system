package com.example.ecommerce.middleware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class LoggingMiddleware implements HandlerInterceptor {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Runs before the controller action
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String timestamp = LocalDateTime.now().format(FORMATTER);
        String method    = request.getMethod();
        String uri       = request.getRequestURI();
        String clientIp  = request.getRemoteAddr();

        System.out.println("*******************************************");
        System.out.println("[REQUEST RECEIVED]");
        System.out.println("  Timestamp  : " + timestamp);
        System.out.println("  Method     : " + method);
        System.out.println("  URI        : " + uri);
        System.out.println("  Client IP  : " + clientIp);

        // Store the start time so we can calculate duration in afterCompletion
        request.setAttribute("startTime", System.currentTimeMillis());

        return true;
    }

    // Runs after the controller action but before the response is sent
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        System.out.println("  Status     : " + response.getStatus());
    }

    // Runs after the full request-response cycle is complete
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {

        long startTime = (Long) request.getAttribute("startTime");
        long duration  = System.currentTimeMillis() - startTime;

        System.out.println("  Duration   : " + duration + "ms");
        if (ex != null) {
            System.out.println("  Error      : " + ex.getMessage());
        }
        System.out.println("*******************************************");
    }
}