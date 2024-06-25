package com.lyfter.backend.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WorkoutLogRequest {

    private Integer workoutId;
    private Integer userId;

    @NotNull
    private List<ExerciseSetsRequest> exercises;

    @NotNull
    private LocalTime length;
}
