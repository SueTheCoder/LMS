package com.lms.repository;

import com.lms.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUserId(Long userId);

    //if you are updating (DML ops in JPA repo you should your annotation

    @Query("UPDATE User u SET u.userName=:userName,u.firstName=:firstName,u.lastName=:lastName," +
            "u.phoneNumber=:phoneNumber,u.email=:email,u.address=:address,u.zipCode=:zipCode,u.dateOfJoin=:dateOfJoin,u.dob=:dob WHERE u.userId=:userId")
    @Modifying
    void update(@Param("userId") Long userId,
                @Param("userName") String userName,
                @Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("email") String email,
                @Param("phoneNumber") String phoneNumber,
                @Param("zipCode") String zipCode,
                @Param("address") String address,
                @Param("dateOfJoin") LocalDateTime dateOfJoin,
                @Param("dob") LocalDateTime dob
    );
}
