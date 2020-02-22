package com.example.giveandtake.repository;

import com.example.giveandtake.domain.RoleName;
import com.example.giveandtake.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);
}
