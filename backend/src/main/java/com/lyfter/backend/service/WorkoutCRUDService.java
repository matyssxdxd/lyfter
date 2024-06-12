package com.lyfter.backend.service;

import com.lyfter.backend.model.Workout;
import com.lyfter.backend.payload.request.WorkoutRequest;

import java.util.List;

public interface WorkoutCRUDService {

    List<Workout> getAllWorkouts() throws Exception;

    Workout getWorkoutById(Integer id) throws Exception;

    List<Workout> getAllWorkoutsByUserId(Integer id) throws Exception;

    void addWorkout(WorkoutRequest addWorkoutRequest) throws Exception;

    void updateWorkout(WorkoutRequest request, Integer id) throws Exception;

    void deleteWorkout(Integer id) throws Exception;
}
