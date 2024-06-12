package com.lyfter.backend.service.impl;

import com.lyfter.backend.model.Exercise;
import com.lyfter.backend.model.User;
import com.lyfter.backend.model.Workout;
import com.lyfter.backend.payload.request.WorkoutRequest;
import com.lyfter.backend.repo.ExerciseRepository;
import com.lyfter.backend.repo.UserRepository;
import com.lyfter.backend.repo.WorkoutRepository;
import com.lyfter.backend.service.WorkoutCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutCRUDServiceImpl implements WorkoutCRUDService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Override
    public List<Workout> getAllWorkouts() throws Exception {
        if (workoutRepository.findAll().isEmpty()) throw new Exception("There are no workouts.");

        return workoutRepository.findAll();
    }

    @Override
    public Workout getWorkoutById(Integer id) throws Exception {
        if (id < 0) throw new Exception("Workout id must be greater than 0.");

        return workoutRepository.findById(id)
                .orElseThrow(() -> new Exception("There is no workout with id " + id));
    }

    @Override
    public List<Workout> getAllWorkoutsByUserId(Integer id) throws Exception {
        if (id < 0) throw new Exception("User id must be greater than 0.");

        List<Workout> result = workoutRepository.findByUserId(id);

        if (result.isEmpty()) throw new Exception("User with id " + id + " has no workouts.");

        return result;
    }

    @Override
    public void addWorkout(WorkoutRequest request) throws Exception {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("User with id " + request.getUserId() + " does not exist."));

        List<Exercise> exercises = new ArrayList<>();

        for (Integer exerciseId : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(exerciseId)
                    .orElseThrow(() -> new Exception("Exercise with id " + exerciseId + " does not exist."));
            exercises.add(exercise);
        }

        Workout workout = new Workout();
        workout.setName(request.getName());
        workout.setDescription(request.getDescription());
        workout.setExercises(exercises);
        workout.setUser(user);

        workoutRepository.save(workout);
    }

    @Override
    public void updateWorkout(WorkoutRequest request, Integer id) throws Exception {
        if (id < 0) throw new Exception("Workout id must be greater than 0.");
        if (!workoutRepository.existsById(id)) throw new Exception("There is no workout with id " + id);

        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new Exception("There is no workout with id " + id));

        List<Exercise> exercises = new ArrayList<>();

        for (Integer exerciseId : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(exerciseId)
                    .orElseThrow(() -> new Exception("Exercise with id " + exerciseId + " does not exist."));
            exercises.add(exercise);
        }

        workout.setName(request.getName());
        workout.setDescription(request.getDescription());
        workout.setExercises(exercises);

        workoutRepository.save(workout);
    }

    @Override
    public void deleteWorkout(Integer id) throws Exception {
        if (id < 0) throw new Exception("Workout id must be greater than 0.");
        if (!workoutRepository.existsById(id)) throw new Exception("There is no workout with id " + id);

        workoutRepository.deleteById(id);
    }
}
