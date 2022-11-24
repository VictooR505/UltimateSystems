package com.company.ultimatesystems.teacher.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public record TeacherOperationsDTO(
        @Size(min = 2)
        String firstName,
        String lastName,
        @Min(value = 18)
        int age,
        @Email
        String email,
        String subject
) {
}
