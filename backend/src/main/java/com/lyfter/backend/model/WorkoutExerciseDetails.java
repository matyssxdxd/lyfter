package com.lyfter.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "workout_exercise_details")
public class WorkoutExerciseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private float weight;

    @NotNull
    private Integer reps;

    @ManyToOne
    private WorkoutExercise workoutExercise;
}
