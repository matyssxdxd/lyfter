package com.lyfter.backend.payload.request;

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
    private List<ExerciseSetsRequest> exercises;
    private LocalTime length;
}
