package com.lms.domain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "rooms")
@Entity
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    @Column(length = 50,nullable = false,unique = true)
    private String roomName;
    @Column(length = 50,nullable = false)
    private Integer capacity ;
    @Column(nullable = false)
    private Boolean availability ;

}
