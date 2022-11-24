package com.company.ultimatesystems.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findAllByFirstNameAndLastName(String firstName, String lastName);
    List<Teacher> findAllByLastName(String lastName);
    List<Teacher> findAllByFirstName(String firstName);
    Teacher findByEmail(String email);
    boolean existsByEmail(String email);
}
