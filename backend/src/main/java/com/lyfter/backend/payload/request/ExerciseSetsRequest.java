package com.lyfter.backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExerciseSetsRequest {
    private Integer exerciseId;
    private List<SetsRequest> sets;
}