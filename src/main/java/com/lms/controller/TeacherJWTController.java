package com.lms.controller;

import com.lms.dto.TeacherDTO;
import com.lms.dto.request.LoginRequest;
import com.lms.dto.request.TeacherRegisterRequest;
import com.lms.dto.response.LoginResponse;
import com.lms.dto.response.ResponseMessage;
import com.lms.dto.response.VRResponse;
import com.lms.security.jwt.JwtUtils;
import com.lms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TeacherJWTController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    @PreAuthorize("(hasRole('ADMIN'))")
    @PostMapping("/teacher-register")
    public ResponseEntity<VRResponse> registerTeacher(@Valid @RequestBody TeacherRegisterRequest teacherRegisterRequest) {
        teacherService.saveTeacher(teacherRegisterRequest);
        VRResponse response = new VRResponse();
        response.setMessage(ResponseMessage.REGISTER_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
//        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        String jwtToken = jwtUtils.generateToken(userDetails);
//        LoginResponse loginResponse = new LoginResponse(jwtToken);
//
//        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
//    }
}