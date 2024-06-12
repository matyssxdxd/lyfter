package com.lyfter.backend.repo;

import com.lyfter.backend.model.ExerciseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseDetailsRepository extends JpaRepository<ExerciseDetails, Integer> {
}
