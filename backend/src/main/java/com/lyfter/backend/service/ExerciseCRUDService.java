package com.lyfter.backend.service;

import com.lyfter.backend.model.Exercise;
import com.lyfter.backend.model.MuscleGroup;
import com.lyfter.backend.model.MuscleGroupEnum;
import com.lyfter.backend.payload.request.ExerciseRequest;

import java.util.List;

public interface ExerciseCRUDService {

    List<Exercise> getAllExercises() throws Exception;

    Exercise getExerciseById(int id) throws Exception;

    List<Exercise> getExercisesByMuscleGroup(MuscleGroupEnum muscleGroup) throws Exception;

    void addExercise(ExerciseRequest request) throws Exception;

    void updateExerciseById(ExerciseRequest request, Integer id) throws Exception;

    void deleteExerciseById(Integer id) throws Exception;
}
