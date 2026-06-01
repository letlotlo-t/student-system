package com.example.ecommerce.student;

import com.example.ecommerce.entity.Course;
import com.example.ecommerce.service.CacheService;
import com.example.ecommerce.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/performance")
public class NotificationController {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private SessionService sessionService;

    // SSE emitters — one per connected client
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // GET courses — served from cache after first call
    @GetMapping("/courses")
    public List<Course> getCourses() {
        return cacheService.getAllCourses();
    }

    // POST course — saves and clears cache, then notifies all clients
    @PostMapping("/courses")
    public Course addCourse(@RequestBody Course course) {
        Course saved = cacheService.saveCourse(course);
        notifyClients("New course added: " + saved.getCourseCode());
        return saved;
    }

    // Add course to session cart
    @PostMapping("/cart/add")
    public List<String> addToCart(@RequestParam String courseCode, HttpSession session) {
        sessionService.addToCart(session, courseCode);
        return sessionService.getCart(session);
    }

    // View session cart
    @GetMapping("/cart")
    public List<String> getCart(HttpSession session) {
        return sessionService.getCart(session);
    }

    // SSE endpoint — clients connect here to receive real-time notifications
    @GetMapping("/notifications")
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    // Push notification to all connected clients
    private void notifyClients(String message) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                deadEmitters.add(emitter);
            }
        }
        emitters.removeAll(deadEmitters);
    }
}