package com.lyfter.backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExerciseSetsRequest {
    private Integer exerciseId;
    private Integer reps;
    private float weight;
}
