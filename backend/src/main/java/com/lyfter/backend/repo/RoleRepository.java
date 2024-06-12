package com.lyfter.backend.repo;

import com.lyfter.backend.model.Role;
import com.lyfter.backend.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleEnum name);

}
