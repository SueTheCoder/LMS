package com.lms.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {
    @NotBlank
    private String oldPassword;
    @Size(min = 4, max = 20, message = "Please provide correct size for password")
    @NotBlank(message = "Please provide your password")
    private String newPassword;


}
