package com.lyfter.backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class WorkoutRequest {
    private Integer userId;
    private String name;
    private String description;
    private List<Integer> exercises;
}
