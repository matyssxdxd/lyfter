package com.lyfter.backend.service;

import com.lyfter.backend.model.WorkoutLog;
import com.lyfter.backend.payload.request.WorkoutLogRequest;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutLogCRUDService {

    List<WorkoutLog> getAllWorkoutLogs() throws Exception;

    List<WorkoutLog> getWorkoutLogsByUserId(int userId) throws Exception;

    List<WorkoutLog> getWorkoutLogsByDateAndUserId(LocalDate date, int userId) throws Exception;

    void saveWorkoutLog(WorkoutLogRequest request) throws Exception;

    void deleteWorkoutLog(int id) throws Exception;
}
