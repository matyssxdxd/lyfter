package com.lyfter.backend.service.impl;

import com.lyfter.backend.model.Exercise;
import com.lyfter.backend.model.MuscleGroup;
import com.lyfter.backend.repo.ExerciseRepository;
import com.lyfter.backend.service.ExerciseCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseCRUDServiceImpl implements ExerciseCRUDService {

    @Autowired
    ExerciseRepository exerciseRepo;

    @Override
    public List<Exercise> getAllExercises() throws Exception {
        if (exerciseRepo.findAll().isEmpty()) throw new Exception("There are no exercises.");

        return exerciseRepo.findAll();
    }

    @Override
    public Exercise getExerciseById(int id) throws Exception {
        if (id < 0) throw new Exception("Exercise id must be greater than 0.");

        return exerciseRepo.findById(id)
                .orElseThrow(() -> new Exception("Exercise id " + id + " not found."));
    }

    @Override
    public List<Exercise> getExercisesByMuscleGroup(MuscleGroup muscleGroup) throws Exception {
        if (muscleGroup == null) throw new Exception("Muscle group must be provided.");

        List<Exercise> ex = exerciseRepo.findByMuscleGroupsContaining(muscleGroup);

        if (ex.isEmpty()) throw new Exception("Exercises with muscle group " + muscleGroup + " not found.");

        return ex;
    }

    @Override
    public void addExercise(Exercise exercise) throws Exception {
        if (exercise == null) throw new Exception("Exercise must be provided.");
        if (exerciseRepo.findByName(exercise.getName())) throw new Exception("Exercise with name " + exercise.getName() + " already exists.");

        exerciseRepo.save(exercise);
    }

    @Override
    public void updateExerciseById(Exercise exercise, Integer id) throws Exception {
        if (exercise == null) throw new Exception("Exercise must be provided.");
        if (id < 0) throw new Exception("Exercise id must be greater than 0.");

        Exercise ex = exerciseRepo.findById(id)
                .orElseThrow(() -> new Exception("Exercise with id " + id + " was not found."));

        ex.setName(exercise.getName());
        ex.setDescription(exercise.getDescription());
        ex.setMuscleGroups(ex.getMuscleGroups());

        exerciseRepo.save(ex);
    }

    @Override
    public void deleteExerciseById(Integer id) throws Exception {
        if (id < 0) throw new Exception("Exercise id must be greater than 0.");
        if (!exerciseRepo.existsById(id)) throw new Exception("Exercise with id " + id + " was not found.");

        exerciseRepo.deleteById(id);
    }
}
