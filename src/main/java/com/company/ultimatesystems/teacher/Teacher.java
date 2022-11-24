package com.company.ultimatesystems.teacher;

import com.company.ultimatesystems.student.Student;
import com.company.ultimatesystems.teacher.dto.TeacherOperationsDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;
    @Size(min = 2)
    private String firstName;
    private String lastName;
    @Min(value = 18)
    private int age;
    @Email
    private String email;
    private String subject;
    @ManyToMany(mappedBy = "teachers")
    @JsonIgnore
    Set<Student> students = new HashSet<>();

    public Teacher(Long id, String firstName, String lastName, int age, String email, String subject, Set<Student> students) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.subject = subject;
        this.students = students;
    }

    public Teacher() {
    }

    public void addStudent(Student student){
        this.students.add(student);
        student.getTeachers().add(this);
    }

    public void removeStudent(Student student){
        this.students.remove(student);
        student.getTeachers().remove(this);
    }

    public void setEntityFields(TeacherOperationsDTO source, Teacher target){
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
        if(source.subject() !=null){
            target.setSubject(source.subject());
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return age == teacher.age && Objects.equals(firstName, teacher.firstName) && Objects.equals(lastName, teacher.lastName)
                && Objects.equals(email, teacher.email) && Objects.equals(subject, teacher.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age, email, subject);
    }
}
