package com.lyfter.backend.repo;

import com.lyfter.backend.model.WorkoutExerciseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExerciseDetailsRepository extends JpaRepository<WorkoutExerciseDetails, Integer> {
}
