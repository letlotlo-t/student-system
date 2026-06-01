package com.example.ecommerce.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegistrationService {

    // In-memory store — simulates a database for this exercise
    private final Map<Integer, List<String>> studentRegistrations = new HashMap<>();
    private static final int MAX_COURSES = 5;

    public boolean register(Integer studentId, String courseCode) {
        studentRegistrations.putIfAbsent(studentId, new ArrayList<>());
        List<String> courses = studentRegistrations.get(studentId);

        if (courses.size() >= MAX_COURSES) {
            return false; // max course load reached
        }

        courses.add(courseCode);
        return true;
    }

    public int getCourseCount(Integer studentId) {
        return studentRegistrations.getOrDefault(studentId, new ArrayList<>()).size();
    }

    public List<String> getRegisteredCourses(Integer studentId) {
        return studentRegistrations.getOrDefault(studentId, new ArrayList<>());
    }
}