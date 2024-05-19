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

@Table(name = "grades")
@Entity
public class Grades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradeId;
    @Column(length = 50, nullable = false, unique = true)
    private Long studentId;
    @Column(length = 50, nullable = false)
    private Long courseId;
    @Column(length = 50, nullable = true)
    private String gradeValue;
    @Column(length = 50, nullable = true)
    private String whoChanged;
    @Column(nullable = false)
    private Boolean isFinal;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
