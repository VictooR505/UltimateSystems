package com.company.ultimatesystems.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByFirstNameAndLastName(String firstName, String lastName);
    List<Student> findAllByFirstName(String firstName);
    List<Student> findAllByLastName(String lastName);
    Student findByEmail(String email);
    boolean existsByEmail(String email);
}
