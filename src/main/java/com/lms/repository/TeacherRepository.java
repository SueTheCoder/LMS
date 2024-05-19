package com.lms.repository;

import com.lms.domain.Teacher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<Teacher> findByEmail(String email);

    Boolean existsByEmail(String email);
    Boolean existsByTeacherNo(String teacherNo);

    @EntityGraph(attributePaths = "roles")
    Optional<Teacher> findByTeacherId(Long teacherId);
    @EntityGraph(attributePaths = "roles")
    Optional<Teacher> findByTeacherNo(String teacherNo);
    //if you are updating (DML ops in JPA repo you should use annotation

    @Query("UPDATE Teacher u SET u.teacherNo=:teacherNo,u.branch=:branch,u.firstName=:firstName,u.lastName=:lastName," +
            "u.phoneNumber=:phoneNumber,u.email=:email,u.address=:address,u.zipCode=:zipCode,u.dateOfJoin=:dateOfJoin," +
            "u.dob=:dob,u.dateOfLeave=:dateOfLeave,u.status=:status " +
            "WHERE u.teacherId=:teacherId")
    @Modifying
    void update(@Param("teacherId") Long teacherId,
                @Param("teacherNo") String teacherNo,
                @Param("branch") String branch,
                @Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("email") String email,
                @Param("phoneNumber") String phoneNumber,
                @Param("zipCode") String zipCode,
                @Param("address") String address,
                @Param("dateOfJoin") LocalDateTime dateOfJoin,
                @Param("dob") LocalDateTime dob,
                @Param("dateOfLeave") LocalDateTime dateOfLeave,
                @Param("status") String status
    );
}
