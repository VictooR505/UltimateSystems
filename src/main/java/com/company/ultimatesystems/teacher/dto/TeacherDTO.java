package com.company.ultimatesystems.teacher.dto;

public record TeacherDTO(
        Long id,
        String firstName,
        String lastName,
        int age,
        String email,
        String subject) {
}
