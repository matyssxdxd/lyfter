package com.lyfter.backend.controller;

import com.lyfter.backend.model.Exercise;
import com.lyfter.backend.model.MuscleGroup;
import com.lyfter.backend.service.ExerciseCRUDService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/exercise")
public class ExerciseController {

    @Autowired
    ExerciseCRUDService exerciseCRUDService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllExercises() {
        try {
            return ResponseEntity.ok(exerciseCRUDService.getAllExercises());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getExercisesById(@RequestParam int id) {
        try {
            return ResponseEntity.ok(exerciseCRUDService.getExerciseById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getExercisesByMuscleGroup(@RequestParam MuscleGroup muscleGroup) {
        try {
            return ResponseEntity.ok(exerciseCRUDService.getExercisesByMuscleGroup(muscleGroup));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addExercise(@Valid @RequestBody Exercise exercise) {
        try {
            exerciseCRUDService.addExercise(exercise);
            return ResponseEntity.ok("Exercise added successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateExercise(@Valid @RequestBody Exercise exercise, @RequestParam int id) {
        try {
            exerciseCRUDService.updateExerciseById(exercise, id);
            return ResponseEntity.ok("Exercise updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteExercise(@RequestParam int id) {
        try {
            exerciseCRUDService.deleteExerciseById(id);
            return ResponseEntity.ok("Exercise deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
