package com.example.registrationlogindemo.service.impl;


import com.example.registrationlogindemo.dto.StudentDto;
import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.repository.StudentRepository;
import com.example.registrationlogindemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<StudentDto> getStudentsDto(){
        List<Student> students = studentRepository.findAll();
        return students.stream().map((student) -> convertEntityToDto(student))
                .collect(Collectors.toList());
    }

    private StudentDto convertEntityToDto(Student student){
        StudentDto studentDto = new StudentDto();
        studentDto.setNationality(student.getNationality());
        studentDto.setStudies(student.getStudies());
        return studentDto;
    }

    public Student findByEmail(String email) {
        return studentRepository.findStudentByUser_Id(userRepository.findByEmail(email).getId());
    }

    public List<Student> getStudentsWithCourse(String title){
        return studentRepository.findAllByCourseTitle(title);
    }

    public Student findByUserId(Long id) {
        return studentRepository.findStudentByUser_Id(id);
    }

    public Student deleteStudent(Student student){
        studentRepository.delete(student);
        return student;
    }

    public Student saveStudent(Student student) {
        Role role = roleRepository.findByName("ROLE_STUDENT");
        if(role == null){
            role = checkRoleExist("ROLE_STUDENT");
        }
        student.getUser().getRoles().add(role);
        userRepository.save(student.getUser());
        studentRepository.save(student);
        return student;
    }
    public Student getStudentByUserId(Long user_id) {
        return studentRepository.findStudentByUser_Id(user_id);
    }
    public List<Student> geStudentsWithStatusPending(){
        return studentRepository.findAllByStatus("Pending");
    }
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Role checkRoleExist(String role) {
        Role rolee = new Role();
        rolee.setName(role);
        return roleRepository.save(rolee);
    }
}
