package com.lms.controller;

import com.lms.dto.request.AdminUserUpdateRequest;
import com.lms.dto.request.UpdatePasswordRequest;
import com.lms.dto.request.UserUpdateRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import com.lms.dto.UserDTO;
import com.lms.dto.response.ResponseMessage;
import com.lms.dto.response.VRResponse;
import com.lms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("/auth/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        UserDTO userDTO = userService.getPrincipal();
        return ResponseEntity.ok(userDTO);

    }

    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("{id}/auth")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserByUserId(userId);
        return ResponseEntity.ok(userDTO);

    }

    @PreAuthorize("(hasRole('ADMIN'))")
    @DeleteMapping("{userId}/auth")
    public ResponseEntity<VRResponse> deleteUser(@PathVariable Long userId) {
        userService.removeUserByUserId(userId);
        VRResponse response = new VRResponse();
        response.setMessage(ResponseMessage.USER_DELETE_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("/auth/pages")
    public ResponseEntity<Page<UserDTO>> getAllUsersByPage(@RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam("sort") String prop,
                                                           @RequestParam(value = "direction",required = false,
                                                                   defaultValue = "DESC") Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<UserDTO> userDTOPage = userService.getUserPage(pageable);
        return ResponseEntity.ok(userDTOPage);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @PutMapping("/user-update")
    public ResponseEntity<VRResponse> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        userService.updateUser(userUpdateRequest);
        VRResponse response = new VRResponse();
        response.setMessage(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("(hasRole('ADMIN'))")
    @PutMapping("/{id}/auth")
    public ResponseEntity<VRResponse> updateUserAuth(@PathVariable Long id,
                                                     @Valid @RequestBody AdminUserUpdateRequest adminUserUpdateRequest
    ) {
        userService.updateUserAuth(id, adminUserUpdateRequest);
        VRResponse response = new VRResponse();
        response.setMessage(ResponseMessage.USER_UPDATE_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasRole('ADMIN') ")
    @PatchMapping("/auth")
    public ResponseEntity<VRResponse> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {

        userService.updatePassword(updatePasswordRequest);
        VRResponse response = new VRResponse();
        response.setMessage(ResponseMessage.USER_PASSWORD_CHANGED_MESSAGE);
        response.setSuccess(true);
        return ResponseEntity.ok(response);
    }


}
