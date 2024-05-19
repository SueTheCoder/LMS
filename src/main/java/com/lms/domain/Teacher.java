package com.lms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "teachers")
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;
    @Column(length = 50,nullable = false,unique = true)
    private String teacherNo;
    @Column(length = 50)
    private String branch;
    @Column(length = 50,nullable = false)
    private String firstName;
    @Column(length = 50,nullable = false)
    private String lastName;
    @Column(length = 80,nullable = true,unique = true)
    private String email;
    @Column(length = 120,nullable = false)
    private String password;
    @Column(length = 14,nullable = true)
    private String phoneNumber;
    @Column(length = 100,nullable = true)
    private String address;
    @Column(length = 15,nullable = true)
    private String zipCode;
    @Column(length = 14,nullable = false)
    private LocalDateTime dob;
    @Column(nullable = false)
    private LocalDateTime dateOfJoin;
    @Column(nullable = true)
    private LocalDateTime dateOfLeave;
    @Column(length = 30,nullable = false)
    private String status; // lay off, transfer...
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "teacher_role", joinColumns = @JoinColumn(name="teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

}
