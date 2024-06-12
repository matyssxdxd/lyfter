package com.lyfter.backend.repo;

import com.lyfter.backend.model.MuscleGroup;
import com.lyfter.backend.model.MuscleGroupEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Integer> {

    Optional<MuscleGroup> findByName(MuscleGroupEnum name);
}
