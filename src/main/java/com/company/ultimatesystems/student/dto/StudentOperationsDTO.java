package com.company.ultimatesystems.student.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public record StudentOperationsDTO(
        @Size(min = 2)
        String firstName,
        String lastName,
        @Min(value = 18)
        int age,
        @Email
        String email,
        String fieldOfStudy
) {
}
