package com.lyfter.backend.repo;

import com.lyfter.backend.model.Exercise;
import com.lyfter.backend.model.MuscleGroup;
import com.lyfter.backend.model.MuscleGroupEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {
    boolean findByName(String name);

    List<Exercise> findByMuscleGroupsName(MuscleGroupEnum muscleGroup);

    boolean existsByName(String name);
}
