package com.lyfter.backend.repo;

import com.lyfter.backend.model.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Integer> {

    List<WorkoutLog> findByUserId(int userId);

    List<WorkoutLog> findByUserIdAndDate(int userId, LocalDate date);
}
