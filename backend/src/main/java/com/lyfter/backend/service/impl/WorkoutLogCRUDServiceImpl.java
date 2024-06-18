package com.lyfter.backend.service.impl;

import com.lyfter.backend.model.*;
import com.lyfter.backend.payload.request.ExerciseSetsRequest;
import com.lyfter.backend.payload.request.SetsRequest;
import com.lyfter.backend.payload.request.WorkoutLogRequest;
import com.lyfter.backend.repo.*;
import com.lyfter.backend.service.WorkoutLogCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutLogCRUDServiceImpl implements WorkoutLogCRUDService {

    @Autowired
    private WorkoutLogRepository workoutLogRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseSetsRepository exerciseSetsRepository;

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
        if (request == null) throw new Exception("Workout log must be provided.");

        Workout workout = workoutRepository.findById(request.getWorkoutId())
                .orElseThrow(() -> new Exception("There is no workout with id " + request.getWorkoutId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("There is no user with id " + request.getUserId()));

        WorkoutLog workoutLog = new WorkoutLog();

        workoutLog.setWorkout(workout);
        workoutLog.setUser(user);
        workoutLog.setDate(LocalDate.now());
        workoutLog.setLength(request.getLength());

        workoutLogRepository.save(workoutLog);

        List<ExerciseSets> exerciseSets = new ArrayList<>();

        for (ExerciseSetsRequest es : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(es.getExerciseId())
                    .orElseThrow(() -> new Exception("There is no exercise with id " + es.getExerciseId()));

            for (SetsRequest set : es.getSets()) {
                ExerciseSets exerciseSet = new ExerciseSets();

                exerciseSet.setExercise(exercise);
                exerciseSet.setReps(set.getReps());
                exerciseSet.setWeight(set.getWeight());

                exerciseSets.add(exerciseSet);
            }
        }

        for (ExerciseSets exerciseSet : exerciseSets) {
            exerciseSet.setWorkoutLog(workoutLog);
            exerciseSetsRepository.save(exerciseSet);
        }

    }

    @Override
    public void deleteWorkoutLog(int id) throws Exception {

    }
}
