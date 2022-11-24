package com.company.ultimatesystems.teacher;

import com.company.ultimatesystems.exception.ObjectNotExistsException;
import com.company.ultimatesystems.student.Student;
import com.company.ultimatesystems.student.StudentRepository;
import com.company.ultimatesystems.teacher.dto.TeacherDTO;
import com.company.ultimatesystems.teacher.dto.TeacherOperationsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.teacherMapper = new TeacherMapper();
    }

    public Page<TeacherDTO> getAllTeachers(String sortBy, String sortOrder, int page, int size){
        if(!sortOrder.isEmpty() && !sortOrder.equalsIgnoreCase("asc") && !sortOrder.equalsIgnoreCase("desc")){
            throw new IllegalArgumentException("Sort order must be ascending (ASC) or descending (DESC)");
        }
        return teacherRepository.findAll(PageRequest.of(page, size, Sort.Direction.valueOf(sortOrder.toUpperCase()), sortBy))
                .map(teacherMapper::map);
    }

    public List<TeacherDTO> getTeachersByName(String firstName, String lastName){
        if(firstName!=null && lastName!=null){
            return teacherRepository.findAllByFirstNameAndLastName(firstName, lastName).stream().map(teacherMapper::map).toList();
        }else if(firstName==null &&lastName!=null){
            return teacherRepository.findAllByLastName(lastName).stream().map(teacherMapper::map).toList();
        }
        else if(firstName!=null){
            return teacherRepository.findAllByFirstName(firstName).stream().map(teacherMapper::map).toList();
        }
        throw new IllegalArgumentException("Please enter name");
    }

    public void addTeacher(TeacherOperationsDTO teacherDTO){
        if(teacherRepository.existsByEmail(teacherDTO.email())){
            throw new IllegalArgumentException("Teacher with email " + teacherDTO.email() + " is already registered");
        }
        teacherRepository.save(teacherMapper.map(teacherDTO));
    }

    public void removeTeacher(Long id){
        if(!teacherRepository.existsById(id)){
            throw new ObjectNotExistsException("Teacher with id " + id + " doesn't exists");
        }
        teacherRepository.deleteById(id);
    }

    public void updateTeacher(Long id, TeacherOperationsDTO teacherDTO){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(
                () -> new ObjectNotExistsException("Teacher with id " + id + "doesn't exists"));
        teacher.setEntityFields(teacherDTO, teacher);
        teacherRepository.save(teacher);
    }
    public void addStudentToTeacher(Long teacherId, String studentEmail){
        if(!studentRepository.existsByEmail(studentEmail)){
            throw new ObjectNotExistsException("Student with email " + studentEmail + " doesn't exists");
        }
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new ObjectNotExistsException("Teacher with id " + teacherId + " doesn't exists"));
        Student student = studentRepository.findByEmail(studentEmail);
        if(teacher.getStudents().contains(student)){
            throw new IllegalArgumentException("Student with email " + studentEmail + " is already assigned to teacher");
        }
        teacher.addStudent(student);
        teacherRepository.save(teacher);
    }

    public void removeStudentFromTeacher(Long teacherId, String studentEmail){
        if(!studentRepository.existsByEmail(studentEmail)){
            throw new ObjectNotExistsException("Student with email " + studentEmail + " doesn't exists");
        }
        Student student = studentRepository.findByEmail(studentEmail);
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new ObjectNotExistsException("Teacher with id " + teacherId + " doesn't exists"));
        if(!teacher.getStudents().contains(student)){
            throw new IllegalArgumentException("Student with email " + studentEmail + " is not assigned to teacher");
        }
        teacher.removeStudent(student);
        teacherRepository.save(teacher);
        studentRepository.save(student);
    }

    public List<Student> getTeacherStudents(Long id){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(
                () -> new ObjectNotExistsException("Teacher with id " + id + " doesn't exists"));
        return teacher.getStudents().stream().toList();
    }
}
