package com.example.ecommerce.student;

import com.example.ecommerce.model.CourseRegistration;
import com.example.ecommerce.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("registration", new CourseRegistration());
        return "registration-form";
    }

    @PostMapping("/submit")
    public String submitForm(
            @Valid @ModelAttribute("registration") CourseRegistration registration,
            BindingResult result,
            Model model) {

        try {
            if (result.hasErrors()) {
                return "registration-form";
            }

            boolean success = registrationService.register(
                    registration.getStudentId(),
                    registration.getCourseCode()
            );

            if (!success) {
                model.addAttribute("businessError",
                        "You have reached the maximum of 5 courses.");
                return "registration-form";
            }

            model.addAttribute("successMessage",
                    "Successfully registered for " + registration.getCourseCode() + ".");
            model.addAttribute("courseCount",
                    registrationService.getCourseCount(registration.getStudentId()));
            model.addAttribute("registration", new CourseRegistration());
            return "registration-form";

        } catch (Exception e) {
            model.addAttribute("businessError",
                    "An unexpected error occurred: " + e.getMessage());
            model.addAttribute("registration", new CourseRegistration());
            return "registration-form";
        }
    }
}