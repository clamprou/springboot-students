package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.StudentDto;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.service.impl.CourseService;
import com.example.registrationlogindemo.service.impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
//@RequestMapping(path = "")
public class StudentController {
    private final StudentService studentService;

    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public StudentController(StudentService studentService, CourseService courseService, UserService userService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping(path = "myapi/students")
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping(path = "myapi/student")
    public Student newStudent(@RequestBody StudentDto studentDto){
        if (userService.findByEmail(studentDto.getUser_email()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email: "+ studentDto.getUser_email() +" doesnt exists!");
        }
        if(studentService.findByEmail(studentDto.getUser_email()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student with email: "+ studentDto.getUser_email() +" already exists!");
        }
        if(courseService.getCourse(studentDto.getCourse_title()) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course with title: "+ studentDto.getCourse_title() +" doesnt exists!");
        }
        Student student = new Student();
        student.setStatus("Pending");
        student.setStudies(studentDto.getStudies());
        student.setNationality(studentDto.getNationality());
        student.setAddress(studentDto.getAddress());
        student.setPhone(studentDto.getPhone());
        student.setBirth(studentDto.getBirth());
        student.setWhy(studentDto.getWhy());
        student.setCourse(courseService.getCourse(studentDto.getCourse_title()));

        User user = userService.findByEmail(studentDto.getUser_email());
        student.setUser(user);
        return studentService.saveStudent(student);
    }
}
