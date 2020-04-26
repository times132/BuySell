package com.buysell.repository;

import com.buysell.domain.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from user_roles where user_id=?1", nativeQuery=true)
    void deleteAllByUserId(Long user_id);
}
