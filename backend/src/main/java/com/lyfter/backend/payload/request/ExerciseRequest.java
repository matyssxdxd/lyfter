package com.lyfter.backend.payload.request;

import com.lyfter.backend.model.MuscleGroupEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExerciseRequest {
    private String name;
    private String description;
    private List<MuscleGroupEnum> muscleGroups;
}
