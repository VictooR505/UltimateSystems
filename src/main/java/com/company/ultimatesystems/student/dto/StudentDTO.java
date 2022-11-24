package com.company.ultimatesystems.student.dto;

public record StudentDTO(
        Long id,
        String firstName,
        String lastName,
        int age,
        String email,
        String fieldOfStudy) {
}
