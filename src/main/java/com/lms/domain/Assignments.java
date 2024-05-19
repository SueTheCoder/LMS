package com.lms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.hpsf.Decimal;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "assignments")
@Entity
public class Assignments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignId;
    @Column(length = 50, nullable = false)
    private Long courseId;
    @Column(length = 100, nullable = false)
    private String assignType;
    @Column(length = 50, nullable = false)
    private Double assignValue;
    @Column(nullable = false)
    private LocalDateTime assignDate;
    @Column(nullable = false)
    private LocalDateTime endDate;



}
