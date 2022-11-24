package com.company.ultimatesystems.teacher;

import com.company.ultimatesystems.student.Student;
import com.company.ultimatesystems.teacher.dto.TeacherDTO;
import com.company.ultimatesystems.teacher.dto.TeacherOperationsDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/name")
    public List<TeacherDTO> getTeachersByName(@RequestParam(required = false) String firstName,
                                              @RequestParam(required = false) String lastName){
        return teacherService.getTeachersByName(firstName, lastName);
    }

    @GetMapping
    public Page<TeacherDTO> getAllTeachers(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "id") String sortBy,
                                        @RequestParam(defaultValue = "ASC") String sortOrder){
        return teacherService.getAllTeachers(sortBy, sortOrder, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTeacher(@Valid @RequestBody TeacherOperationsDTO teacher){
        teacherService.addTeacher(teacher);
    }

    @DeleteMapping("/{id}")
    public void removeTeacher(@PathVariable("id") Long id){
        teacherService.removeTeacher(id);
    }

    @PatchMapping("/{id}")
    public void updateTeacher(@PathVariable("id") Long id, @Valid @RequestBody TeacherOperationsDTO teacherDTO){
        teacherService.updateTeacher(id, teacherDTO);
    }

    @PatchMapping("/{id}/students/add")
    public void addStudentToTeacher(@PathVariable("id") Long id, @RequestBody String studentEmail) {
        teacherService.addStudentToTeacher(id, studentEmail);
    }

    @PatchMapping("/{id}/students/remove")
    public void removeStudentFromTeacher(@PathVariable("id") Long id, @RequestBody String studentEmail){
        teacherService.removeStudentFromTeacher(id, studentEmail);
    }

    @GetMapping("/{id}/students")
    public List<Student> getTeacherStudents(@PathVariable("id") Long id){
        return teacherService.getTeacherStudents(id);
    }
}
