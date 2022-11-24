package com.company.ultimatesystems.teacher;

import com.company.ultimatesystems.teacher.dto.TeacherDTO;
import com.company.ultimatesystems.teacher.dto.TeacherOperationsDTO;

public class TeacherMapper {

    public TeacherMapper() {
    }

    public TeacherDTO map(Teacher teacher){
        TeacherDTO teacherDTO = new TeacherDTO(
                teacher.getId(),
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getAge(),
                teacher.getEmail(),
                teacher.getSubject()
        );
        return teacherDTO;
    }

    public Teacher map(TeacherOperationsDTO teacherDTO){
        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDTO.firstName());
        teacher.setLastName(teacherDTO.lastName());
        teacher.setAge(teacherDTO.age());
        teacher.setEmail(teacherDTO.email());
        teacher.setSubject(teacherDTO.subject());
        return teacher;
    }
}
