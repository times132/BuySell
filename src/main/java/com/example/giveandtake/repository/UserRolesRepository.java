package com.example.giveandtake.repository;

import com.example.giveandtake.model.entity.User;
import com.example.giveandtake.model.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    @Modifying
    @Transactional
    @Query(value = "delete from user_roles where user_id=?1", nativeQuery=true)
    void deleteAllByUserId(Long user_id);
}
