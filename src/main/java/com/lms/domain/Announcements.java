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

@Table(name = "announcements")
@Entity
public class Announcements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long announcementId;
    @Column(length = 50, nullable = false)
    private Long groupId;
    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 200, nullable = true)
    private String body;
    @Column(length = 100)
    private String status;
    @Column(nullable = false)
    private LocalDateTime endDate;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
