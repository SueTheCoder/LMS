package com.lms.service;

import com.lms.domain.Role;
import com.lms.domain.enums.RoleType;
import com.lms.exception.ResourceNotFoundException;
import com.lms.exception.message.ErrorMessage;
import com.lms.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findByType(RoleType roleType) {
        return roleRepository.findByType(roleType).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_EXCEPTION, roleType.getName())));
    }

}

