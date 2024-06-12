package com.lyfter.backend.payload.request;

import com.lyfter.backend.model.ExerciseDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WorkoutLogRequest {
    private Integer workoutId;
    private Integer userId;
    private List<ExerciseDetails> exerciseDetails;
    private LocalDate date;
    private LocalTime length;
}
