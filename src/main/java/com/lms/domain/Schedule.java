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

@Table(name = "schedule")
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @Column(length = 120, nullable = false)
    private String courseName;
    @Column(length = 12, nullable = false)
    private String courseCode;
    @Column(length = 50, nullable = true)
    private String roomName;
    @Column(length = 120, nullable = true)
    private String eventType;
    @Column(nullable = true)
    private LocalDateTime eventDate;
    @Column(nullable = true)
    private LocalDateTime eventTime;
    @Column(length = 120, nullable = true)
    private String eventDescription;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
    @Column(length = 30, nullable = false)
    private String status; // ended, cancelled...


}
