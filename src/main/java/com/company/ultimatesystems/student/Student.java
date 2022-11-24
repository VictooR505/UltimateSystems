package com.company.ultimatesystems.student;

import com.company.ultimatesystems.student.dto.StudentOperationsDTO;
import com.company.ultimatesystems.teacher.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    @Size(min = 2)
    private String firstName;
    private String lastName;
    @Min(value = 18)
    private int age;
    @Email
    private String email;
    private String fieldOfStudy;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    @JoinTable(name = "student_teacher",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teachers = new HashSet<>();

    public Student(Long id, String firstName, String lastName, int age, String email, String fieldOfStudy, Set<Teacher> teachers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.fieldOfStudy = fieldOfStudy;
        this.teachers = teachers;
    }

    public Student() {
    }

    public void addTeacher(Teacher teacher){
        this.teachers.add(teacher);
        teacher.getStudents().add(this);
    }

    public void removeTeacher(Teacher teacher){
        this.teachers.remove(teacher);
        teacher.getStudents().remove(this);
    }

    public void setEntityFields(StudentOperationsDTO source, Student target){
        if(source.firstName() != null){
            target.setFirstName(source.firstName());
        }
        if(source.lastName() != null){
            target.setLastName(source.lastName());
        }
        target.setAge(source.age());
        if(source.email() != null){
            target.setEmail(source.email());
        }
        if(source.fieldOfStudy() !=null){
            target.setFieldOfStudy(source.fieldOfStudy());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName)
                && Objects.equals(email, student.email) && Objects.equals(fieldOfStudy, student.fieldOfStudy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, email, fieldOfStudy);
    }
}
