package com.lms.mapper;

import com.lms.domain.User;
import com.lms.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(User user);

    List<UserDTO> map(List<User> userList);
}
