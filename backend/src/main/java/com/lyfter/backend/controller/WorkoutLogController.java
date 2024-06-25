package com.lyfter.backend.controller;

import com.lyfter.backend.payload.request.WorkoutLogRequest;
import com.lyfter.backend.payload.response.MessageResponse;
import com.lyfter.backend.service.WorkoutLogCRUDService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/workout-log")
public class WorkoutLogController {

    private final WorkoutLogCRUDService workoutLogCRUDService;

    @Autowired
    public WorkoutLogController(WorkoutLogCRUDService workoutLogCRUDService) {
        this.workoutLogCRUDService = workoutLogCRUDService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addWorkoutLog(@Valid @RequestBody WorkoutLogRequest request) {
        try {
            workoutLogCRUDService.saveWorkoutLog(request);
            return ResponseEntity.ok(new MessageResponse(("Workout log added successfully.")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> findWorkoutLogByUserIdAndDate(@RequestParam int userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        try {
            return ResponseEntity.ok(workoutLogCRUDService.getWorkoutLogsByDateAndUserId(date, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
