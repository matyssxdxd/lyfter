package com.lyfter.backend.controller;

import com.lyfter.backend.model.Workout;
import com.lyfter.backend.payload.request.WorkoutRequest;
import com.lyfter.backend.service.WorkoutCRUDService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/workout")
public class WorkoutController {

    @Autowired
    WorkoutCRUDService workoutCRUDService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllWorkouts() {
        try {
            return ResponseEntity.ok(workoutCRUDService.getAllWorkouts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> getWorkoutById(@RequestParam int id) {
        try {
            return ResponseEntity.ok(workoutCRUDService.getWorkoutById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> getAllWorkoutsByUserId(@RequestParam int userId) {
        try {
            return ResponseEntity.ok(workoutCRUDService.getAllWorkoutsByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addWorkout(@Valid @RequestBody WorkoutRequest request) {
        try {
            workoutCRUDService.addWorkout(request);
            return ResponseEntity.ok("Workout added successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateWorkout(@Valid @RequestBody WorkoutRequest request, @RequestParam int id) {
        try {
            workoutCRUDService.updateWorkout(request, id);
            return ResponseEntity.ok("Workout updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteWorkout(@RequestParam int id) {
        try {
            workoutCRUDService.deleteWorkout(id);
            return ResponseEntity.ok("Workout deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
