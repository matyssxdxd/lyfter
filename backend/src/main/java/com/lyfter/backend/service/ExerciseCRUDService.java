package com.lyfter.backend.service;

import com.lyfter.backend.model.Exercise;
import com.lyfter.backend.model.MuscleGroup;

import java.util.List;

public interface ExerciseCRUDService {

    List<Exercise> getAllExercises() throws Exception;

    Exercise getExerciseById(int id) throws Exception;

    List<Exercise> getExercisesByMuscleGroup(MuscleGroup muscleGroup) throws Exception;

    void addExercise(Exercise exercise) throws Exception;

    void updateExerciseById(Exercise exercise, Integer id) throws Exception;

    void deleteExerciseById(Integer id) throws Exception;
}
