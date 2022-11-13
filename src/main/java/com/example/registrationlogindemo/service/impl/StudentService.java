package com.example.registrationlogindemo.service.impl;


import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public Student findByEmail(String email) {
        return null;
    }

    public void saveStudent(Student student) {
        studentRepository.save(student);
    }
    public Student getStudentByUserId(Long user_id) {
        return studentRepository.findStudentByUser_Id(user_id);
    }
    public List<Student> geStudentsWithStatusPending(){
        return studentRepository.findAllByStatus("Pending");
    }
}
