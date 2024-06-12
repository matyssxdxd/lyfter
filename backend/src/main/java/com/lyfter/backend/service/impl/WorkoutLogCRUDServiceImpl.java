package com.lyfter.backend.service.impl;

import com.lyfter.backend.model.WorkoutLog;
import com.lyfter.backend.payload.request.WorkoutLogRequest;
import com.lyfter.backend.repo.WorkoutLogRepository;
import com.lyfter.backend.service.WorkoutLogCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutLogCRUDServiceImpl implements WorkoutLogCRUDService {

    @Autowired
    private WorkoutLogRepository workoutLogRepository;

    @Override
    public List<WorkoutLog> getAllWorkoutLogs() throws Exception {
        if (workoutLogRepository.findAll().isEmpty()) throw new Exception("There are no workout logs.");

        return workoutLogRepository.findAll();
    }

    @Override
    public List<WorkoutLog> getWorkoutLogsByUserId(int userId) throws Exception {
        if (userId < 0) throw new Exception("User id must be greater than 0.");

        List<WorkoutLog> workoutLogs = workoutLogRepository.findByUserId(userId);

        if (workoutLogs.isEmpty()) throw new Exception("There are no workout logs for user with id " + userId);

        return workoutLogs;
    }

    @Override
    public List<WorkoutLog> getWorkoutLogsByDateAndUserId(LocalDate date, int userId) throws Exception {
        if (userId < 0) throw new Exception("User id must be greater than 0.");
        if (date == null) throw new Exception("Date must be provided.");

        List<WorkoutLog> workoutLogs = workoutLogRepository.findByUserIdAndDate(userId, date);

        if (workoutLogs.isEmpty()) throw new Exception("There are no workout logs for user with id " + userId + " at " + date);

        return workoutLogs;
    }

    @Override
    public void saveWorkoutLog(WorkoutLogRequest request) throws Exception {

    }

    @Override
    public void deleteWorkoutLog(int id) throws Exception {

    }
}
