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

@Table(name = "attendance")
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId;
    @Column(length = 50, nullable = false)
    private Long courseId;
    @Column(length = 50, nullable = false)
    private Long studentId;
    @Column(length = 100, nullable = false)
    private String status;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private Short timeOrder;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
