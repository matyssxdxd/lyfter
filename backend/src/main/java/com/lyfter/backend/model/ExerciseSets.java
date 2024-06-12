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
@Table(name = "exercise_sets")
public class ExerciseSets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private Integer reps;

    @NotNull
    private float weight;

    @ManyToOne
    @JoinColumn(name = "exercise_details_id")
    private ExerciseDetails exerciseDetails;
}
