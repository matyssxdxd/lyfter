package com.lyfter.backend.repo;

import com.lyfter.backend.model.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer> {
}
