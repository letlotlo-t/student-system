package com.example.ecommerce.student;

import com.example.ecommerce.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@Tag(name = "Payment API", description = "Handles course fee payment processing")
public class PaymentApiController {

    @Autowired
    private PaymentService paymentService;

    @Operation(summary = "Process a payment",
            description = "Processes a course fee payment for a student")
    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(
            @Parameter(description = "Student ID") @RequestParam Integer studentId,
            @Parameter(description = "Course code") @RequestParam String courseCode,
            @Parameter(description = "Payment amount") @RequestParam double amount,
            @RequestHeader("X-Api-Key") String apiKey) {

        // API key validation
        if (!"payment-api-key-2025".equals(apiKey)) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid API key.");
            return ResponseEntity.status(401).body(error);
        }

        // Call PaymentService
        String result = paymentService.processPayment(amount);

        // Call external mock API (httpbin.org)
        String externalResponse = callExternalApi(studentId, courseCode, amount);

        Map<String, Object> response = new HashMap<>();
        response.put("studentId", studentId);
        response.put("courseCode", courseCode);
        response.put("amount", amount);
        response.put("status", "SUCCESS");
        response.put("message", result);
        response.put("externalApiResponse", externalResponse);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get payment status",
            description = "Returns the payment status for a given student")
    @GetMapping("/status/{studentId}")
    public ResponseEntity<Map<String, Object>> getPaymentStatus(
            @PathVariable Integer studentId,
            @RequestHeader("X-Api-Key") String apiKey) {

        if (!"payment-api-key-2025".equals(apiKey)) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid API key.");
            return ResponseEntity.status(401).body(error);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("studentId", studentId);
        response.put("status", "PAID");
        response.put("message", "All fees settled for student " + studentId);
        return ResponseEntity.ok(response);
    }

    private String callExternalApi(Integer studentId, String courseCode, double amount) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> payload = new HashMap<>();
            payload.put("studentId", studentId);
            payload.put("courseCode", courseCode);
            payload.put("amount", amount);
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://httpbin.org/post", payload, String.class);
            return "External API responded with status: " + response.getStatusCode();
        } catch (Exception e) {
            return "External API unavailable: " + e.getMessage();
        }
    }
}