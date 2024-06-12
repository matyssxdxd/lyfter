package com.lyfter.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "workout_excercise")
public class WorkoutExercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Exercise exercise;

    @ManyToOne
    private Workout workout;

    @OneToMany
    private List<WorkoutExerciseDetails> workoutExerciseDetails;
}
