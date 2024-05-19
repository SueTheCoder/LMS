package com.lms.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "courses")
@Entity
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    @Column(length = 50,nullable = false)
    private String courseCode;
    @Column(length = 50,nullable = false)
    private String courseName;
    @Column(length = 6,nullable = false)
    private Integer capacity ;
    @Column(length = 6)
    private Integer capacityCount ;
    @ManyToMany
    @JoinTable(name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> studentIds = new HashSet<>();
    @Column(nullable = false)
    private Boolean availability ;
    @Column
    private LocalDateTime classDate;
    @Column
    private LocalDateTime classTime;
    @Column
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
