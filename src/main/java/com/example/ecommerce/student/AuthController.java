package com.example.ecommerce.student;

import com.example.ecommerce.model.UserSession;
import com.example.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLogin(
            @RequestParam(required = false) String logout,
            @ModelAttribute("error") String error,
            Model model) {
        if (logout != null) model.addAttribute("logout", true);
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        UserSession user = authService.authenticate(username, password);

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password. Please try again.");
            return "redirect:/auth/login";
        }

        session.setAttribute("currentUser", user);
        session.setAttribute("X-Session-Token", user.getToken());
        return "redirect:/student/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/login?logout";
    }
}