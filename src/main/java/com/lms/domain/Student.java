package com.lms.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)

@Table(name = "students")
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50,nullable = false,unique = true)
    private String studentNo;
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
    @Column(length = 14,nullable = false,unique = true)
    private String parentPhoneNumber;
    @Column(length = 100,nullable = true)
    private String address;
    @Column(length = 15,nullable = true)
    private String zipCode;
    @Column(length = 14,nullable = false)
    private String dob;
    @Column(length = 100,nullable = false)
    private String parentFullName;
    @Column(length = 80,nullable = false,unique = true)
    private String parentEmail;
    @Column(length = 120,nullable = false)
    private String parentPassword;
    @Column(length = 30,nullable = false)
    private String status;  //alumni , registered, unregistered, leave...
    @Column(nullable = false)
    private Boolean isPaymentOk;
    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @PrePersist
    public void prePersist() {
        registrationDate = LocalDateTime.now();
    }
}
