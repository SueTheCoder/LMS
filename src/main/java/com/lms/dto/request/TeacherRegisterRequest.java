package com.lms.dto.request;

import com.lms.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRegisterRequest {
    @Size(max = 50)
    @NotBlank(message = "Please provide teacher number")
    private String teacherNo;
    @Size(max = 50)
    @NotBlank(message = "Please provide first name")
    private String firstName;
    @Size(max = 50)
    @NotBlank(message = "Please provide last name")
    private String lastName;
    @Size(max = 50)
    @NotBlank(message = "Please provide branch")
    private String branch;
    @Email(message = "Please provide valid email")
    @Size(min = 4, max = 80)
    private String email;
    @Size(min = 4, max = 20, message = "Please provide correct size for password")
    @NotBlank(message = "Please provide password")
    private String password;
    @Size(min = 10, max = 14)
    @NotBlank(message = "Please provide phone number")
    //@Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
    //        message = "Please provide valid phone number")
    private String phoneNumber;
    @Size(max = 100)
    @NotBlank(message = "Please provide address")
    private String address;
    @Size(max = 15)
    private String zipCode;
    @Past(message = "Please provide Birthday")
    private LocalDateTime dob;
    @Past(message = "Please provide date of join")
    private LocalDateTime dateOfJoin;
    private LocalDateTime dateOfLeave;
    private String status;
    private List<RoleType> roles;
}
