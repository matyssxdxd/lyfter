package com.lyfter.backend.controller;

import com.lyfter.backend.model.MuscleGroupEnum;
import com.lyfter.backend.payload.request.ExerciseRequest;
import com.lyfter.backend.payload.response.MessageResponse;
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

    private final ExerciseCRUDService exerciseCRUDService;

    @Autowired
    public ExerciseController(ExerciseCRUDService exerciseCRUDService) {
        this.exerciseCRUDService = exerciseCRUDService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllExercises() {
        try {
            return ResponseEntity.ok(exerciseCRUDService.getAllExercises());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/find")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getExercises(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String muscleGroup) {
        try {
            if (id != null) {
                return ResponseEntity.ok(exerciseCRUDService.getExerciseById(id));
            } else if (muscleGroup != null) {
                return ResponseEntity.ok(exerciseCRUDService.getExercisesByMuscleGroup(MuscleGroupEnum.valueOf(muscleGroup)));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("Either 'id' or 'muscleGroup' parameter must be provided."));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addExercise(@Valid @RequestBody ExerciseRequest request) {
        try {
            exerciseCRUDService.addExercise(request);
            return ResponseEntity.ok(new MessageResponse("Exercise added successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @PatchMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateExercise(@Valid @RequestBody ExerciseRequest request, @RequestParam int id) {
        try {
            exerciseCRUDService.updateExerciseById(request, id);
            return ResponseEntity.ok(new MessageResponse("Exercise updated successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteExercise(@RequestParam int id) {
        try {
            exerciseCRUDService.deleteExerciseById(id);
            return ResponseEntity.ok(new MessageResponse("Exercise deleted successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

}
