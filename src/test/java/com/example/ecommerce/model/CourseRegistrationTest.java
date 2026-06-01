package com.example.ecommerce.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CourseRegistrationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validRegistration_noViolations() {
        CourseRegistration reg = new CourseRegistration();
        reg.setCourseCode("SD501");
        reg.setStudentId(1001);
        reg.setEnrolmentDate(LocalDate.now());
        reg.setCredits(8);

        Set<ConstraintViolation<CourseRegistration>> violations = validator.validate(reg);
        assertTrue(violations.isEmpty(), "Valid registration should have no violations");
    }

    @Test
    void emptyCourseCode_shouldFailValidation() {
        CourseRegistration reg = new CourseRegistration();
        reg.setCourseCode("");
        reg.setStudentId(1001);
        reg.setEnrolmentDate(LocalDate.now());
        reg.setCredits(8);

        Set<ConstraintViolation<CourseRegistration>> violations = validator.validate(reg);
        assertFalse(violations.isEmpty(), "Empty course code should fail");
    }

    @Test
    void nullStudentId_shouldFailValidation() {
        CourseRegistration reg = new CourseRegistration();
        reg.setCourseCode("SD501");
        reg.setStudentId(null);
        reg.setEnrolmentDate(LocalDate.now());
        reg.setCredits(8);

        Set<ConstraintViolation<CourseRegistration>> violations = validator.validate(reg);
        assertFalse(violations.isEmpty(), "Null student ID should fail");
    }

    @Test
    void creditsExceedMax_shouldFailValidation() {
        CourseRegistration reg = new CourseRegistration();
        reg.setCourseCode("SD501");
        reg.setStudentId(1001);
        reg.setEnrolmentDate(LocalDate.now());
        reg.setCredits(50);

        Set<ConstraintViolation<CourseRegistration>> violations = validator.validate(reg);
        assertFalse(violations.isEmpty(), "Credits over 30 should fail");
    }

    @Test
    void courseCodeTooShort_shouldFailValidation() {
        CourseRegistration reg = new CourseRegistration();
        reg.setCourseCode("AB");
        reg.setStudentId(1001);
        reg.setEnrolmentDate(LocalDate.now());
        reg.setCredits(8);

        Set<ConstraintViolation<CourseRegistration>> violations = validator.validate(reg);
        assertFalse(violations.isEmpty(), "Course code under 4 chars should fail");
    }
}