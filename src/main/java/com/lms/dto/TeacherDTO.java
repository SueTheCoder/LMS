package com.lms.dto;

import com.lms.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {

    private Long teacherId;
    private String teacherNo;
    private String branch;
    private String firstName;
    private String lastName;
    private String email;
    // private String password;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private LocalDateTime dob;
    private LocalDateTime dateOfJoin;
    private LocalDateTime dateOfLeave;
    private String status; // lay off, transfer...


}
