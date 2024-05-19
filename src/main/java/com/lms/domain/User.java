package com.lms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "t_user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(length = 50,nullable = false)
    private String userName;
    @Column(length = 50,nullable = false)
    private String firstName;
    @Column(length = 50,nullable = false)
    private String lastName;
    @Column(length = 80,nullable = false,unique = true)
    private String email;
    @Column(length = 120,nullable = false)
    private String password;
    @Column(length = 14)
    private String phoneNumber;
    @Column(length = 100)
    private String address;
    @Column(length = 15)
    private String zipCode;
    @Column
    private LocalDateTime dob;
    @Column
    private LocalDateTime dateOfJoin;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();








}
