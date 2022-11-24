package com.company.ultimatesystems.student;


import com.company.ultimatesystems.student.dto.StudentDTO;
import com.company.ultimatesystems.student.dto.StudentOperationsDTO;
import com.company.ultimatesystems.teacher.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/name")
    public List<StudentDTO> getStudentsByName(@RequestParam(required = false) String firstName,
                                              @RequestParam(required = false) String lastName){
        return studentService.getStudentsByName(firstName, lastName);
    }

    @GetMapping
    public Page<StudentDTO> getAllStudents(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy,
                                        @RequestParam(defaultValue = "ASC") String sortOrder){
        return studentService.getAllStudents(sortBy, sortOrder, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addStudent(@Valid @RequestBody StudentOperationsDTO student){
        studentService.addStudent(student);
    }

    @DeleteMapping("/{id}")
    public void removeStudent(@PathVariable("id") Long id){
        studentService.removeStudent(id);
    }

    @PatchMapping("/{id}")
    public void updateStudent(@PathVariable("id") Long id, @Valid @RequestBody StudentOperationsDTO student){
        studentService.updateStudent(id, student);
    }

    @PatchMapping("/{id}/teachers/add")
    public void addTeacherToStudent(@PathVariable("id") Long id, @RequestBody String teacherEmail) {
        studentService.addTeacherToStudent(id, teacherEmail);
    }

    @PatchMapping("/{id}/teachers/remove")
    public void removeTeacherFromStudent(@PathVariable("id") Long id, @RequestBody String teacherEmail){
        studentService.removeTeacherFromStudent(id, teacherEmail);
    }

    @GetMapping("/{id}/teachers")
    public List<Teacher> getStudentTeachers(@PathVariable("id") Long id){
        return studentService.getStudentTeachers(id);
    }

}
