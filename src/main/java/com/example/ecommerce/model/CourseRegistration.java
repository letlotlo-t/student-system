package com.example.ecommerce.model;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class CourseRegistration {



    @NotBlank(message = "Course code is required.")
    @Size(min = 4, max = 10, message = "Course code must be between 4 and 10 characters.")
    private String courseCode;

    @NotNull(message = "Student ID is required.")
    @Min(value = 1, message = "Student ID must be a positive number.")
    private Integer studentId;

    @NotNull(message = "Enrolment date is required.")
    @PastOrPresent(message = "Enrolment date cannot be in the future.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate enrolmentDate;

    @NotNull(message = "Credits are required.")
    @Min(value = 1, message = "Credits must be at least 1.")
    @Max(value = 30, message = "Credits cannot exceed 30.")
    private Integer credits;

    // Getters and setters
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Integer getStudentId() {
        return studentId;
    }
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public LocalDate getEnrolmentDate() {
        return enrolmentDate;
    }
    public void setEnrolmentDate(LocalDate enrolmentDate) {
        this.enrolmentDate = enrolmentDate;
    }

    public Integer getCredits() {
        return credits;
    }
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}