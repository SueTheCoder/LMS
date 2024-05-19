package com.lms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "groups")
@Entity
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;
    @Column(length = 100, nullable = true)
    private String groupName;

    // TODO: Take a look again hata versi
    //@OneToMany(mappedBy = "groups")
    //private Set<> userIds= new HashSet<>();

    @Column(length = 50, nullable = true)
    private String status;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
