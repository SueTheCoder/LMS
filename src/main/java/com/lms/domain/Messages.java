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
@Entity
@Table(name = "messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msgId;
    @Column(length = 50, nullable = false)
    private String title;
    @Column(length = 200, nullable = false)
    private String body;
    @Column(length = 50, nullable = false)
    private Long senderId;
    @Column(length = 50, nullable = false)
    private Long receiverId;
    @Column(length = 50, nullable = false)
    private String status;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

}