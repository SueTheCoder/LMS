package com.lms.service;

import com.lms.domain.Role;
import com.lms.domain.Teacher;
import com.lms.domain.enums.RoleType;
import com.lms.dto.TeacherDTO;
//import com.lms.dto.request.AdminTeacherUpdateRequest;
import com.lms.dto.request.TeacherRegisterRequest;
import com.lms.dto.request.UpdatePasswordRequest;
//import com.lms.dto.request.TeacherUpdateRequest;
import com.lms.exception.BadRequestException;
import com.lms.exception.ConflictException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.exception.message.ErrorMessage;
import com.lms.mapper.TeacherMapper;
import com.lms.mapper.UserMapper;
import com.lms.repository.TeacherRepository;
import com.lms.security.SecurityUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class TeacherService {

    private TeacherRepository teacherRepository;
    private PasswordEncoder passwordEncoder;
    private TeacherMapper teacherMapper;
    private RoleService roleService;

    public TeacherService(TeacherRepository teacherRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
        this.teacherMapper = teacherMapper;
        this.roleService = roleService;
    }

    public Teacher getTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email)));
    }

    public void saveTeacher(TeacherRegisterRequest teacherRegisterRequest) {
        if (teacherRepository.existsByEmail(teacherRegisterRequest.getEmail())) {
            throw new ConflictException(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE);
        }
        else if (teacherRepository.existsByTeacherNo(teacherRegisterRequest.getTeacherNo())) {
            throw new ConflictException(ErrorMessage.ID_ALREADY_EXIST_MESSAGE);
        }
        //we have to encode our password before saving into DB.
        String encodedPassword = passwordEncoder.encode(teacherRegisterRequest.getPassword());

        //builder design pattern -> @Builder
        Teacher teacher = new Teacher();
        teacher.setTeacherNo(teacherRegisterRequest.getTeacherNo());
        teacher.setBranch(teacherRegisterRequest.getBranch());
        teacher.setFirstName(teacherRegisterRequest.getFirstName());
        teacher.setLastName(teacherRegisterRequest.getLastName());
        teacher.setEmail(teacherRegisterRequest.getEmail());
        teacher.setPassword(encodedPassword);
        teacher.setPhoneNumber(teacherRegisterRequest.getPhoneNumber());
        teacher.setAddress(teacherRegisterRequest.getAddress());
        teacher.setZipCode(teacherRegisterRequest.getZipCode());
        teacher.setDob(teacherRegisterRequest.getDob());
        teacher.setDateOfJoin(teacherRegisterRequest.getDateOfJoin());
        teacher.setDateOfLeave(teacherRegisterRequest.getDateOfLeave());
        teacher.setStatus(teacherRegisterRequest.getStatus());

        List<RoleType> roleTypes = teacherRegisterRequest.getRoles();
        List<Role> roles = new ArrayList<>();
        for (RoleType roleType : roleTypes) {
            Role role = roleService.findByType(roleType);
            roles.add(role);
        }
        teacher.setRoles(new HashSet<>(roles));

        teacherRepository.save(teacher);
    }
/*
// this method may be use later if required
    private static void isPermitted(Teacher teacher) {
        if (teacher.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }
*/
    public List<TeacherDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherDTO> teacherDTOS = teacherMapper.map(teachers);
        return teacherDTOS;
    }

    public TeacherDTO getTeacherByTeacherId(Long teacherId) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, teacherId)));
        return teacherMapper.teacherToTeacherDTO(teacher);
    }


    public TeacherDTO getTeacher() {
        Teacher currentTeacher = getCurrentTeacher();
        TeacherDTO teacherDTO = teacherMapper.teacherToTeacherDTO(currentTeacher);
        return teacherDTO;
    }

    // TODO: getCurrentUserLogin() ?? or getCurrentTeacherLogin()
    public Teacher getCurrentTeacher() {
        String email = SecurityUtils.getCurrentTeacherLogin().orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.PRINCIPAL_NOT_FOUND_MESSAGE)));
        Teacher teacher = getTeacherByEmail(email);
        return teacher;
    }

    public Teacher getByTeacherId(Long teacherId) {
        Teacher teacher = teacherRepository.findByTeacherId(teacherId).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE)));
        return teacher;
    }

    public void removeTeacherByTeacherId(Long teacherId) {
        Teacher teacher = getByTeacherId(teacherId);
      /*  if (teacher.getBuiltIn()) {
            throw new BadRequestException(String.format(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE));
        }
        */
        teacherRepository.deleteById(teacher.getTeacherId());
    }

//    public Page<TeacherDTO> getTeacherPage(Pageable pageable) {
//        Page<Teacher> teacherPage = TeacherRepository.findAll(pageable);
//        return getTeacherDTOPage(teacherPage);
//    }

    /**
     * A custom method that maps Page<Teacher>teacherPage to Page<TeacherDTO>
     *
     * @param teacherPage page of Teachers
     * @return page of TeacherDTOs
     */
    private Page<TeacherDTO> getTeacherDTOPage(Page<Teacher> teacherPage) {
        //Page<TeacherDTO> teacherDTOPage = teacherPage.map(teacherMapper::teacherToTeacherDTO);
        // we are writing a custom functional interface, and we are overriding  apply method here.
        Page<TeacherDTO> teacherDTOPage = teacherPage.map(new Function<Teacher, TeacherDTO>() {
            @Override
            public TeacherDTO apply(Teacher teacher) {
                return teacherMapper.teacherToTeacherDTO(teacher);
            }
        });
        return teacherDTOPage;
    }

//    @Transactional
//    public void updateTeacher(TeacherUpdateRequest teacherUpdateRequest) {
//        Teacher teacher = getCurrentTeacher();
//       // isPermitted(teacher);
//        boolean emailExist = teacherRepository.existsByEmail(teacherUpdateRequest.getEmail());
//        if (emailExist && !teacherUpdateRequest.getEmail().equals(teacher.getEmail())) {
//            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, teacherUpdateRequest.getEmail()));
//        }
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String dateOfJoinString = teacherUpdateRequest.getDateOfJoin().format(formatter);
//        String dobString = teacherUpdateRequest.getDob().format(formatter);
//        teacherRepository.update(teacher.getTeacherId(), teacherUpdateRequest.getTeacherName(), teacherUpdateRequest.getFirstName(), teacherUpdateRequest.getLastName(),
//                teacherUpdateRequest.getEmail(), teacherUpdateRequest.getPhoneNumber(),
//                teacherUpdateRequest.getAddress(), teacherUpdateRequest.getZipCode(), dateOfJoinString, dobString);
//
//    }

//    public void updateTeacherAuth(Long teacherId, AdminTeacherUpdateRequest adminTeacherUpdateRequest) {
//        Teacher teacher = getByTeacherId(teacherId);
//
//       // isPermitted(teacher);
//
//        boolean emailExist = teacherRepository.existsByEmail(adminTeacherUpdateRequest.getEmail());
//
//        if (emailExist && !adminTeacherUpdateRequest.getEmail().equals(teacher.getEmail())) {
//            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, adminTeacherUpdateRequest.getEmail()));
//        }
//
//        if (adminTeacherUpdateRequest.getPassword() == null) {
//
//            List<RoleType> roleTypes = adminTeacherUpdateRequest.getRoles();
//            List<Role> roles = new ArrayList<>();
//            for (RoleType roleType : roleTypes) {
//                Role role = roleService.findByType(roleType);
//                roles.add(role);
//            }
//            teacher.setRoles(new HashSet<>(roles));
//
//            teacher.setTeacherName(adminTeacherUpdateRequest.getTeacherName());
//            teacher.setFirstName(adminTeacherUpdateRequest.getFirstName());
//            teacher.setLastName(adminTeacherUpdateRequest.getLastName());
//            teacher.setEmail(adminTeacherUpdateRequest.getEmail());
//            teacher.setPhoneNumber(adminTeacherUpdateRequest.getPhoneNumber());
//            teacher.setAddress(adminTeacherUpdateRequest.getAddress());
//            teacher.setZipCode(adminTeacherUpdateRequest.getZipCode());
//            teacher.setDateOfJoin(adminTeacherUpdateRequest.getDateOfJoin());
//            teacher.setDob(adminTeacherUpdateRequest.getDob());
//        } else {
//            String encodedPassword = passwordEncoder.encode(adminTeacherUpdateRequest.getPassword());
//            teacher.setPassword(encodedPassword);
//        }
//        teacherRepository.save(teacher);
//    }
//burayı gözden geçir
//    private Set<Role> convertRoles(Set<String> pRoles) {
//        Set<Role> roles = new HashSet<>();
//        //TODO we do not have any custom exception that handles the wrong type of role entry
//        if (pRoles == null) {
//            Role teacherRole = roleService.findByType(RoleType.ROLE_DEAN);
//            roles.add(teacherRole);
//        } else {
//            pRoles.forEach(roleStr -> {
//                if (roleStr.equals(RoleType.ROLE_ADMIN.getName())) {
//                    Role adminRole = roleService.findByType(RoleType.ROLE_ADMIN);
//                    roles.add(adminRole);
//                } else {
//                    Role teacherRole = roleService.findByType(RoleType.ROLE_DEAN);
//                    roles.add(teacherRole);
//                }
//            });
//        }
//        return roles;
//    }

    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {

        Teacher teacher = getCurrentTeacher();
/*
        if (teacher.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

 */
        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), teacher.getPassword())) {
            throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED);
        }
        String hashedPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        teacher.setPassword(hashedPassword);
        teacherRepository.save(teacher);
    }

    public List<Teacher> getTeachers() {
        return teacherRepository.findAll();
    }

}