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
@NoArgsConstructor

@Table(name = "pregrades")
@Entity
public class PreGrades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradeId;
    @Column(length = 50, nullable = false, unique = true)
    private Long studentId;
    @Column(length = 50, nullable = false)
    private Long courseId;
    @Column(length = 50, nullable = true)
    private String gradeValue;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
