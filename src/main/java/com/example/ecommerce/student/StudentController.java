package com.example.ecommerce.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("pageTitle", "Course Registration");
        model.addAttribute("message", "Use this form to register for a course.");
        return "register";
    }

    @GetMapping("/grades")
    public String viewGrades(Model model) {
        model.addAttribute("pageTitle", "My Grades");
        model.addAttribute("message", "Your current grades are displayed below.");
        return "grades";
    }

    @GetMapping("/profile")
    public String updateProfile(Model model) {
        model.addAttribute("pageTitle", "Update Profile");
        model.addAttribute("message", "Update your personal information here.");
        return "profile";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "Student Dashboard");
        model.addAttribute("studentName", "Letlotlo Tshoane");
        model.addAttribute("studentNumber", "STU-2026-001");
        model.addAttribute("studentEmail", "student@geeks.com");
        model.addAttribute("profileForm", new ProfileForm());
        return "dashboard";
    }

    @PostMapping("/profile/save")
    public String saveProfile(@ModelAttribute ProfileForm profileForm, Model model) {
        model.addAttribute("pageTitle", "Student Dashboard");
        model.addAttribute("studentName", profileForm.getFullName());
        model.addAttribute("studentNumber", "STU-2026-001");
        model.addAttribute("studentEmail", profileForm.getEmail());
        model.addAttribute("profileForm", profileForm);
        model.addAttribute("saveSuccess", true);
        return "dashboard";
    }
}