package com.lms.service;

import com.lms.domain.Role;
import com.lms.domain.User;
import com.lms.domain.enums.RoleType;
import com.lms.dto.UserDTO;
import com.lms.dto.request.AdminUserUpdateRequest;
import com.lms.dto.request.RegisterRequest;
import com.lms.dto.request.UpdatePasswordRequest;
import com.lms.dto.request.UserUpdateRequest;
import com.lms.exception.BadRequestException;
import com.lms.exception.ConflictException;
import com.lms.exception.ResourceNotFoundException;
import com.lms.exception.message.ErrorMessage;
import com.lms.mapper.UserMapper;
import com.lms.repository.UserRepository;
import com.lms.security.SecurityUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Service
public class UserService {

    private UserRepository userRepository;

    private RoleService roleService;

    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleService roleService, @Lazy PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, email)));
    }

    public void saveUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE);
        }
        //we have to encode our password before saving into DB.
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        //builder design pattern -> @Builder
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setPhoneNumber(registerRequest.getPhoneNumber());
        user.setAddress(registerRequest.getAddress());
        user.setZipCode(registerRequest.getZipCode());
        user.setDob(registerRequest.getDob());
        user.setDateOfJoin(registerRequest.getDateOfJoin());

        List<RoleType> roleTypes = registerRequest.getRoles();
        List<Role> roles = new ArrayList<>();
        for (RoleType roleType : roleTypes) {
            Role role = roleService.findByType(roleType);
            roles.add(role);
        }

            user.setRoles(new HashSet<>(roles));


        userRepository.save(user);
    }
/*
// this method may be use later if required
    private static void isPermitted(User user) {
        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
    }
*/
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = userMapper.map(users);
        return userDTOS;
    }

    public UserDTO getUserByUserId(Long userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, userId)));
        return userMapper.userToUserDTO(user);
    }


    public UserDTO getPrincipal() {
        User currentUser = getCurrentUser();
        UserDTO userDTO = userMapper.userToUserDTO(currentUser);
        return userDTO;
    }

    public User getCurrentUser() {
        String email = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.PRINCIPAL_NOT_FOUND_MESSAGE)));
        User user = getUserByEmail(email);
        return user;
    }

    public User getByUserId(Long userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE)));
        return user;
    }

    public void removeUserByUserId(Long userId) {
        User user = getByUserId(userId);
      /*  if (user.getBuiltIn()) {
            throw new BadRequestException(String.format(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE));
        }
        */
        userRepository.deleteById(user.getUserId());
    }

    public Page<UserDTO> getUserPage(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return getUserDTOPage(userPage);
    }

    /**
     * A custom method that maps Page<User>userPage to Page<UserDTO>
     *
     * @param userPage page of Users
     * @return page of UserDTOs
     */
    private Page<UserDTO> getUserDTOPage(Page<User> userPage) {
        //Page<UserDTO> userDTOPage = userPage.map(userMapper::userToUserDTO);
        // we are wrriting a custom functional interface, and we are overriting  apply method here.
        Page<UserDTO> userDTOPage = userPage.map(new Function<User, UserDTO>() {
            @Override
            public UserDTO apply(User user) {
                return userMapper.userToUserDTO(user);
            }
        });
        return userDTOPage;
    }

    @Transactional
    public void updateUser(UserUpdateRequest userUpdateRequest) {
        User user = getCurrentUser();
       // isPermitted(user);
        boolean emailExist = userRepository.existsByEmail(userUpdateRequest.getEmail());
        if (emailExist && !userUpdateRequest.getEmail().equals(user.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, userUpdateRequest.getEmail()));
        }
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //String dateOfJoinString = userUpdateRequest.getDateOfJoin().format(formatter);
        //String dobString = userUpdateRequest.getDob().format(formatter);
        userRepository.update(
                            user.getUserId(),
                            userUpdateRequest.getUserName(),
                            userUpdateRequest.getFirstName(),
                            userUpdateRequest.getLastName(),
                            user.getEmail(),
                            userUpdateRequest.getPhoneNumber(),
                            userUpdateRequest.getZipCode(),
                            userUpdateRequest.getAddress(),
                            user.getDateOfJoin(),
                            user.getDob()
        );
    }

    public void updateUserAuth(Long userId, AdminUserUpdateRequest adminUserUpdateRequest) {
        User user = getByUserId(userId);

       // isPermitted(user);

        boolean emailExist = userRepository.existsByEmail(adminUserUpdateRequest.getEmail());

        if (emailExist && !adminUserUpdateRequest.getEmail().equals(user.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST_MESSAGE, adminUserUpdateRequest.getEmail()));
        }

        if (adminUserUpdateRequest.getPassword() == null) {

            List<RoleType> roleTypes = adminUserUpdateRequest.getRoles();
            List<Role> roles = new ArrayList<>();
            for (RoleType roleType : roleTypes) {
                Role role = roleService.findByType(roleType);
                roles.add(role);
            }
            user.setRoles(new HashSet<>(roles));

            user.setUserName(adminUserUpdateRequest.getUserName());
            user.setFirstName(adminUserUpdateRequest.getFirstName());
            user.setLastName(adminUserUpdateRequest.getLastName());
            user.setEmail(adminUserUpdateRequest.getEmail());
            user.setPhoneNumber(adminUserUpdateRequest.getPhoneNumber());
            user.setAddress(adminUserUpdateRequest.getAddress());
            user.setZipCode(adminUserUpdateRequest.getZipCode());
            user.setDateOfJoin(adminUserUpdateRequest.getDateOfJoin());
            user.setDob(adminUserUpdateRequest.getDob());
        } else {
            String encodedPassword = passwordEncoder.encode(adminUserUpdateRequest.getPassword());
            user.setPassword(encodedPassword);
        }
        userRepository.save(user);
    }
////bursyı gözden geçir
//    private Set<Role> convertRoles(Set<String> pRoles) {
//        Set<Role> roles = new HashSet<>();
//        //TODO we do not have any custom exception that handles the wrong type of role entry
//        if (pRoles == null) {
//            Role userRole = roleService.findByType(RoleType.ROLE_DEAN);
//            roles.add(userRole);
//        } else {
//            pRoles.forEach(roleStr -> {
//                if (roleStr.equals(RoleType.ROLE_ADMIN.getName())) {
//                    Role adminRole = roleService.findByType(RoleType.ROLE_ADMIN);
//                    roles.add(adminRole);
//                } else {
//                    Role userRole = roleService.findByType(RoleType.ROLE_DEAN);
//                    roles.add(userRole);
//                }
//            });
//        }
//        return roles;
//    }

    public void updatePassword(UpdatePasswordRequest updatePasswordRequest) {

        User user = getCurrentUser();
/*
        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

 */
        if (!passwordEncoder.matches(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED);
        }
        String hashedPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

}