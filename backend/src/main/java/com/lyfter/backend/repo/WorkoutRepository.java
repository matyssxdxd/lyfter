package com.lyfter.backend.repo;

import com.lyfter.backend.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    List<Workout> findByUserId(Integer id);
}
