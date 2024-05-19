package com.lms;

import com.lms.domain.Role;
import com.lms.domain.User;
import com.lms.domain.enums.RoleType;
import com.lms.repository.RoleRepository;
import com.lms.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class LearningManagementSystemApp {
    public static void main(String[] args) {
        SpringApplication.run(LearningManagementSystemApp.class, args);
    }
}

@Component
@AllArgsConstructor
class DemoCommandLineRunner implements CommandLineRunner {

    RoleRepository roleRepository;

    PasswordEncoder encoder;

    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {


        if (!roleRepository.findByType(RoleType.ROLE_ADMIN).isPresent()) {
            Role roleAdmin = new Role();
            roleAdmin.setType(RoleType.ROLE_ADMIN);
            roleRepository.save(roleAdmin);
        }
        if (!userRepository.findByEmail("superadmin@gmail.com").isPresent()) {
            User admin = new User();
            admin.setUserName("admin");
            Role role = roleRepository.findByType(RoleType.ROLE_ADMIN).get();
            admin.setRoles(new HashSet<>(Collections.singletonList(role)));
            admin.setAddress("super user address");
            admin.setEmail("superadmin@gmail.com");
            admin.setFirstName("adminfirstname");
            admin.setLastName("adminlastname");
            admin.setZipCode("123456");
            admin.setPassword(encoder.encode("qe4trm."));
            admin.setPhoneNumber("(541) 317-8828");
            userRepository.save(admin);
        }
    }

}
