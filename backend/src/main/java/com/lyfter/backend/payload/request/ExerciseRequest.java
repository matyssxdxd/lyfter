package com.lyfter.backend.payload.request;

import com.lyfter.backend.model.MuscleGroupEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExerciseRequest {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private List<MuscleGroupEnum> muscleGroups;
}
