package com.SuperMarket.QUINTET_BackEnd.Repository;

import com.SuperMarket.QUINTET_BackEnd.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    User findUserByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.role.id IN (:roles)")
    List<User> findByRoleNames(@Param("roles") List<String> roles);

    @Query("SELECT u FROM User u WHERE u.role.id = :roleName")
    List<User> findByRoleName(@Param("roleName") long roleName);

    @Query("SELECT u FROM User u WHERE u.userProfile.id= :profileId")
    User findByprofileId(long profileId);

    @Query("SELECT u FROM User u WHERE u.userProfile.id= :profileId")
    Optional<User> findUserByprofileId(long profileId);
}
