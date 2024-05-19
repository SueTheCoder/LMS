package com.lms.dto;

import com.lms.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    //private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private LocalDateTime dob;
    private LocalDateTime dateOfJoin;
    //private Boolean builtIn;
    private Set<String> roles;

    public void setRoles(Set<Role> roles) {
        Set<String> roleStr = new HashSet<>();
        roles.forEach(r -> roleStr.add(r.getType().getName()));
        this.roles = roleStr;
    }
}
