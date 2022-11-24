package com.company.ultimatesystems.student;

import com.company.ultimatesystems.exception.ObjectNotExistsException;
import com.company.ultimatesystems.student.dto.StudentDTO;
import com.company.ultimatesystems.student.dto.StudentOperationsDTO;
import com.company.ultimatesystems.teacher.Teacher;
import com.company.ultimatesystems.teacher.TeacherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.studentMapper = new StudentMapper();
    }
    public Page<StudentDTO> getAllStudents(String sortBy, String sortOrder, int page, int size){
        if(!sortOrder.isEmpty() && !sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc")){
            throw new IllegalArgumentException("Sort order must be ascending (ASC) or descending (DESC)");
        }
        return studentRepository.findAll(PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder.toUpperCase()), sortBy))
                .map(studentMapper::map);
    }

    public List<StudentDTO> getStudentsByName(String firstName, String lastName){
        if(firstName!=null && lastName!=null){
            return studentRepository.findAllByFirstNameAndLastName(firstName, lastName).stream().map(studentMapper::map).toList();
        }else if(firstName==null &&lastName!=null){
            return studentRepository.findAllByLastName(lastName).stream().map(studentMapper::map).toList();
        }
        else if(firstName!=null){
            return studentRepository.findAllByFirstName(firstName).stream().map(studentMapper::map).toList();
        }
        throw new IllegalArgumentException("Please enter name");
    }

    public void addStudent(StudentOperationsDTO student){
        if(studentRepository.existsByEmail(student.email())){
            throw new IllegalArgumentException("Student already exists");
        }
        studentRepository.save(studentMapper.map(student));
    }

    public void removeStudent(Long id){
        if(!studentRepository.existsById(id)){
            throw new ObjectNotExistsException("Student with id " + id + " doesn't exists");
        }
        studentRepository.deleteById(id);
    }

    public void updateStudent(Long id, StudentOperationsDTO studentDTO){
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ObjectNotExistsException("Student with id " + id + "doesn't exists"));
        student.setEntityFields(studentDTO, student);
        studentRepository.save(student);
    }
    public void addTeacherToStudent(Long studentId, String teacherEmail){
        if(!teacherRepository.existsByEmail(teacherEmail)){
            throw new ObjectNotExistsException("Teacher with email " + teacherEmail + " doesn't exists");
        }
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ObjectNotExistsException("Student with id " + studentId + " doesn't exists"));
        Teacher teacher = teacherRepository.findByEmail(teacherEmail);
        if(student.getTeachers().contains(teacher)){
            throw new IllegalArgumentException("Teacher with email " + teacherEmail + " is already assigned to student");
        }
        student.addTeacher(teacher);
        studentRepository.save(student);
    }

    public void removeTeacherFromStudent(Long studentId, String teacherEmail){
        if(!teacherRepository.existsByEmail(teacherEmail)){
            throw new ObjectNotExistsException("Teacher with email " + teacherEmail + " doesn't exists");
        }
        Teacher teacher = teacherRepository.findByEmail(teacherEmail);
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new ObjectNotExistsException("Student with id " + studentId + " doesn't exists"));
        if(!student.getTeachers().contains(teacher)){
            throw new ObjectNotExistsException("Teacher with email " + teacherEmail + " is not assigned to student");
        }
        student.removeTeacher(teacher);
        teacherRepository.save(teacher);
        studentRepository.save(student);
    }

    public List<Teacher> getStudentTeachers(Long id){
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ObjectNotExistsException("Student with id " + id + " doesn't exists"));
        return student.getTeachers().stream().toList();
    }

}
