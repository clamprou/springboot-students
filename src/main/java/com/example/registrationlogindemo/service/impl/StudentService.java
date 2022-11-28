package com.example.registrationlogindemo.service.impl;


import com.example.registrationlogindemo.dto.StudentDto;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDto> getStudentsDto(){
        List<Student> students = studentRepository.findAll();
        return students.stream().map((student) -> convertEntityToDto(student))
                .collect(Collectors.toList());
    }

    private StudentDto convertEntityToDto(Student student){
        StudentDto studentDto = new StudentDto();
        studentDto.setDegree(student.getDegree());
        studentDto.setStudies(student.getStudies());
        return studentDto;
    }

    public Student findByEmail(String email) {
        return null;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
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
}
