package com.lms.mapper;

import com.lms.domain.Teacher;
import com.lms.dto.TeacherDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherDTO teacherToTeacherDTO(Teacher teacher);

    List<TeacherDTO> map(List<Teacher> teacherList);
}
