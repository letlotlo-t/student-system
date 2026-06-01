package com.example.ecommerce.service;

import com.example.ecommerce.entity.Course;
import com.example.ecommerce.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CacheService {

    @Autowired
    private CourseRepository courseRepository;

    // Cache the course list — result stored under key "courses"
    @Cacheable("courses")
    public List<Course> getAllCourses() {
        System.out.println(">>> Fetching courses from DATABASE (not cache)");
        return courseRepository.findAll();
    }

    // Clear the cache when a new course is added
    @CacheEvict(value = "courses", allEntries = true)
    public Course saveCourse(Course course) {
        System.out.println(">>> Cache cleared after new course saved");
        return courseRepository.save(course);
    }
}