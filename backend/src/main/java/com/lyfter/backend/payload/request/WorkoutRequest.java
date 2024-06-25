package com.lyfter.backend.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WorkoutRequest {
    private Integer userId;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private List<Integer> exercises;
}
