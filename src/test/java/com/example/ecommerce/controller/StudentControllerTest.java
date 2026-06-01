package com.example.ecommerce.controller;

import com.example.ecommerce.student.StudentController;
import com.example.ecommerce.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ecommerce.filter.SessionValidationFilter;
import com.example.ecommerce.middleware.LoggingMiddleware;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionValidationFilter sessionValidationFilter;

    @MockBean
    private LoggingMiddleware loggingMiddleware;

    @Test
    void registerEndpoint_returnsOk() throws Exception {
        mockMvc.perform(get("/student/register"))
                .andExpect(status().isOk());
    }

    @Test
    void gradesEndpoint_returnsOk() throws Exception {
        mockMvc.perform(get("/student/grades"))
                .andExpect(status().isOk());
    }

    @Test
    void profileEndpoint_returnsOk() throws Exception {
        mockMvc.perform(get("/student/profile"))
                .andExpect(status().isOk());
    }
}