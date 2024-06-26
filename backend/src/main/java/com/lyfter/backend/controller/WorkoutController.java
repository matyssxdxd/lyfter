package com.lyfter.backend.controller;

import com.lyfter.backend.payload.request.WorkoutRequest;
import com.lyfter.backend.payload.response.MessageResponse;
import com.lyfter.backend.service.WorkoutCRUDService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/workout")
public class WorkoutController {

    private final WorkoutCRUDService workoutCRUDService;

    @Autowired
    public WorkoutController(WorkoutCRUDService workoutCRUDService) {
        this.workoutCRUDService = workoutCRUDService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllWorkouts() {
        try {
            return ResponseEntity.ok(workoutCRUDService.getAllWorkouts());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getWorkouts(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer userId) {
        try {
            if (id != null) {
                return ResponseEntity.ok(workoutCRUDService.getWorkoutById(id));
            } else if (userId != null) {
                return ResponseEntity.ok(workoutCRUDService.getAllWorkoutsByUserId(userId));
            } else {
                return ResponseEntity.badRequest().body("Either 'id' or 'userId' parameter must be provided");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> addWorkout(@Valid @RequestBody WorkoutRequest request) {
        try {
            workoutCRUDService.addWorkout(request);
            return ResponseEntity.ok(new MessageResponse(("Workout added successfully.")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/update")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateWorkout(@Valid @RequestBody WorkoutRequest request, @RequestParam int id) {
        try {
            workoutCRUDService.updateWorkout(request, id);
            return ResponseEntity.ok(new MessageResponse("Workout updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteWorkout(@RequestParam int id) {
        try {
            workoutCRUDService.deleteWorkout(id);
            return ResponseEntity.ok(new MessageResponse("Workout deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
