package com.company.ultimatesystems.student;

import com.company.ultimatesystems.student.dto.StudentDTO;
import com.company.ultimatesystems.student.dto.StudentOperationsDTO;

public class StudentMapper {

    public StudentMapper() {
    }

    public StudentDTO map(Student student){
        StudentDTO studentDTO = new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getAge(),
                student.getEmail(),
                student.getFieldOfStudy()
        );
        return studentDTO;
    }

    public Student map(StudentOperationsDTO studentDTO){
        Student student = new Student();
        student.setFirstName(studentDTO.firstName());
        student.setLastName(studentDTO.lastName());
        student.setAge(studentDTO.age());
        student.setEmail(studentDTO.email());
        student.setFieldOfStudy(studentDTO.fieldOfStudy());
        return student;
    }
}
