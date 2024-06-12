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

    @Autowired
    ExerciseRepository exerciseRepo;

    @Autowired
    MuscleGroupRepository muscleGroupRepo;
    @Autowired
    private MuscleGroupRepository muscleGroupRepository;

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
    public List<Exercise> getExercisesByMuscleGroup(MuscleGroupEnum muscleGroup) throws Exception {
        if (muscleGroup == null) throw new Exception("Muscle group must be provided.");

        List<Exercise> ex = exerciseRepo.findByMuscleGroupsName(muscleGroup);

        if (ex.isEmpty()) throw new Exception("Exercises with muscle group " + muscleGroup + " not found.");

        return ex;
    }

    @Override
    public void addExercise(ExerciseRequest request) throws Exception {
        if (request == null) throw new Exception("Exercise must be provided.");
        if (exerciseRepo.existsByName(request.getName())) throw new Exception("Exercise with name " + request.getName() + " already exists.");

        Set<MuscleGroup> muscleGroups = new HashSet<>();

        for (MuscleGroupEnum muscleGroup : request.getMuscleGroups()) {
            MuscleGroup mg = muscleGroupRepository.findByName(muscleGroup)
                    .orElseThrow(() -> new Exception("Muscle group " + muscleGroup + " not found."));
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
        if (id < 0) throw new Exception("Exercise id must be greater than 0.");

        Exercise ex = exerciseRepo.findById(id)
                .orElseThrow(() -> new Exception("Exercise with id " + id + " was not found."));

        Set<MuscleGroup> muscleGroups = new HashSet<>();

        for (MuscleGroupEnum muscleGroup : request.getMuscleGroups()) {
            MuscleGroup mg = muscleGroupRepository.findByName(muscleGroup)
                    .orElseThrow(() -> new Exception("Muscle group " + muscleGroup + " not found."));
            muscleGroups.add(mg);
        }

        ex.setName(request.getName());
        ex.setDescription(request.getDescription());
        ex.setMuscleGroups(muscleGroups);

        exerciseRepo.save(ex);
    }

    @Override
    public void deleteExerciseById(Integer id) throws Exception {
        if (id < 0) throw new Exception("Exercise id must be greater than 0.");
        if (!exerciseRepo.existsById(id)) throw new Exception("Exercise with id " + id + " was not found.");

        exerciseRepo.deleteById(id);
    }
}
