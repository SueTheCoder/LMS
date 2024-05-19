package com.lms.dto.request;

import com.lms.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminTeacherUpdateRequest {

    @Size(max = 50)
    private String userName;
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email(message = "Please provide valid email")
    @Size(min = 5, max = 80)
    private String email;

    @Size(min = 4, max = 20, message = "Please provide correct size for password")
    private String password;

    //@Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",

    @Size(min = 10, max = 14)
    private String phoneNumber;

    @Size(max = 100)
    private String address;

    @Size(max = 15)
    private String zipCode;

    private LocalDateTime dob;
    private LocalDateTime dateOfJoin;
    //    private Boolean builtIn;
    private List<RoleType> roles;


    private LocalDateTime dateOfLeave;
    private String status;


}
