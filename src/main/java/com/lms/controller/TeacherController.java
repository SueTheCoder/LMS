package com.lms.controller;

import com.lms.dto.TeacherDTO;
import com.lms.dto.request.AdminTeacherUpdateRequest;
import com.lms.dto.request.UpdatePasswordRequest;
import com.lms.dto.request.TeacherUpdateRequest;
import com.lms.dto.response.ResponseMessage;
import com.lms.dto.response.VRResponse;
import com.lms.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {


    @Autowired
    private TeacherService teacherService;

    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("/auth/all")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers() {
        List<TeacherDTO> allTeachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(allTeachers);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<TeacherDTO> getTeacher() {
        TeacherDTO teacherDTO = teacherService.getTeacher();
        return ResponseEntity.ok(teacherDTO);

    }

    @PreAuthorize("(hasRole('ADMIN'))")
    @GetMapping("/{id}/auth")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable("id") Long teacherId) {
        TeacherDTO teacherDTO = teacherService.getTeacherByTeacherId(teacherId);
        return ResponseEntity.ok(teacherDTO);

    }

//    @PreAuthorize("(hasRole('ADMIN'))")
//    @DeleteMapping("{teacherId}/auth")
//    public ResponseEntity<VRResponse> deleteTeacher(@PathVariable Long teacherId) {
//        teacherService.removeTeacherByTeacherId(teacherId);
//        VRResponse response = new VRResponse();
//        response.setMessage(ResponseMessage.TEACHER_DELETE_RESPONSE_MESSAGE);
//        response.setSuccess(true);
//        return ResponseEntity.ok(response);
//
//    }

//    @PreAuthorize("(hasRole('ADMIN'))")
//    @GetMapping("/auth/pages")
//    public ResponseEntity<Page<TeacherDTO>> getAllTeachersByPage(@RequestParam("page") int page,
//                                                           @RequestParam("size") int size,
//                                                           @RequestParam("sort") String prop,
//                                                           @RequestParam(value = "direction",required = false,
//                                                                   defaultValue = "DESC") Direction direction) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
//        Page<TeacherDTO> teacherDTOPage = teacherService.getTeacherPage(pageable);
//        return ResponseEntity.ok(teacherDTOPage);
//    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
//    @PutMapping("/teacher-update")
//    public ResponseEntity<VRResponse> updateTeacher(@Valid @RequestBody TeacherUpdateRequest teacherUpdateRequest) {
//        teacherService.updateTeacher(teacherUpdateRequest);
//        VRResponse response = new VRResponse();
//        response.setMessage(ResponseMessage.TEACHER_UPDATE_RESPONSE_MESSAGE);
//        response.setSuccess(true);
//        return ResponseEntity.ok(response);
//    }

//    @PreAuthorize("(hasRole('ADMIN'))")
//    @PutMapping("/{id}/auth")
//    public ResponseEntity<VRResponse> updateTeacherAuth(@PathVariable Long id,
//                                                     @Valid @RequestBody AdminTeacherUpdateRequest adminTeacherUpdateRequest
//    ) {
//        teacherService.updateTeacherAuth(id, adminTeacherUpdateRequest);
//        VRResponse response = new VRResponse();
//        response.setMessage(ResponseMessage.TEACHER_UPDATE_RESPONSE_MESSAGE);
//        response.setSuccess(true);
//        return ResponseEntity.ok(response);
//
//    }

//    @PreAuthorize("hasRole('ADMIN') ")
//    @PatchMapping("/auth")
//    public ResponseEntity<VRResponse> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
//
//        teacherService.updatePassword(updatePasswordRequest);
//        VRResponse response = new VRResponse();
//        response.setMessage(ResponseMessage.TEACHER_PASSWORD_CHANGED_MESSAGE);
//        response.setSuccess(true);
//        return ResponseEntity.ok(response);
//    }


}
