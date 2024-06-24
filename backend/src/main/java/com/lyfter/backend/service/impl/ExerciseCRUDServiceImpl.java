package com.lyfter.backend.service.impl;

import com.lyfter.backend.model.Exercise;
import com.lyfter.backend.model.MuscleGroup;
import com.lyfter.backend.model.MuscleGroupEnum;
import com.lyfter.backend.payload.request.ExerciseRequest;
import com.lyfter.backend.repo.ExerciseRepository;
import com.lyfter.backend.repo.MuscleGroupRepository;
import com.lyfter.backend.service.ExerciseCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExerciseCRUDServiceImpl implements ExerciseCRUDService {

    private static final String EXERCISE_ID_ERROR = "Exercise id must be greater than 0.";
    private static final String NOT_FOUND_ERROR = "not found";

    private final ExerciseRepository exerciseRepo;
    private final MuscleGroupRepository muscleGroupRepository;

    @Autowired
    ExerciseCRUDServiceImpl(ExerciseRepository exerciseRepository, MuscleGroupRepository musclegroupRepository) {
        this.exerciseRepo = exerciseRepository;
        this.muscleGroupRepository = musclegroupRepository;
    }

    @Override
    public List<Exercise> getAllExercises() throws Exception {
        if (exerciseRepo.findAll().isEmpty()) throw new Exception("There are no exercises.");

        return exerciseRepo.findAll();
    }

    @Override
    public Exercise getExerciseById(int id) throws Exception {
        if (id < 0) throw new Exception(EXERCISE_ID_ERROR);

        return exerciseRepo.findById(id)
                .orElseThrow(() -> new Exception("Exercise id " + id + NOT_FOUND_ERROR));
    }

    @Override
    public List<Exercise> getExercisesByMuscleGroup(MuscleGroupEnum muscleGroup) throws Exception {
        if (muscleGroup == null) throw new Exception("Muscle group must be provided.");

        List<Exercise> ex = exerciseRepo.findByMuscleGroupsName(muscleGroup);

        if (ex.isEmpty()) throw new Exception("Exercises with muscle group " + muscleGroup + NOT_FOUND_ERROR);

        return ex;
    }

    @Override
    public void addExercise(ExerciseRequest request) throws Exception {
        if (request == null) throw new Exception("Exercise must be provided.");
        if (exerciseRepo.existsByName(request.getName())) throw new Exception("Exercise with name " + request.getName() + " already exists.");

        Set<MuscleGroup> muscleGroups = new HashSet<>();

        for (MuscleGroupEnum muscleGroup : request.getMuscleGroups()) {
            MuscleGroup mg = muscleGroupRepository.findByName(muscleGroup)
                    .orElseThrow(() -> new Exception("Muscle group " + muscleGroup + NOT_FOUND_ERROR));
            muscleGroups.add(mg);
        }

        Exercise exercise = new Exercise();

        exercise.setName(request.getName());
        exercise.setDescription(request.getDescription());
        exercise.setMuscleGroups(muscleGroups);

        exerciseRepo.save(exercise);
    }

    @Override
    public void updateExerciseById(ExerciseRequest request, Integer id) throws Exception {
        if (request == null) throw new Exception("Exercise must be provided.");
        if (id < 0) throw new Exception(EXERCISE_ID_ERROR);

        Exercise ex = exerciseRepo.findById(id)
                .orElseThrow(() -> new Exception("Exercise with id " + id + " was not found."));

        Set<MuscleGroup> muscleGroups = new HashSet<>();

        for (MuscleGroupEnum muscleGroup : request.getMuscleGroups()) {
            MuscleGroup mg = muscleGroupRepository.findByName(muscleGroup)
                    .orElseThrow(() -> new Exception("Muscle group " + muscleGroup + NOT_FOUND_ERROR));
            muscleGroups.add(mg);
        }

        ex.setName(request.getName());
        ex.setDescription(request.getDescription());
        ex.setMuscleGroups(muscleGroups);

        exerciseRepo.save(ex);
    }

    @Override
    public void deleteExerciseById(Integer id) throws Exception {
        if (id < 0) throw new Exception(EXERCISE_ID_ERROR);
        if (!exerciseRepo.existsById(id)) throw new Exception("Exercise with id " + id + " was not found.");

        exerciseRepo.deleteById(id);
    }
}
