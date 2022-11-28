package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.StudentDto;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.service.impl.CourseService;
import com.example.registrationlogindemo.service.impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "myapi/studentsdto")
    public List<StudentDto> getStudentsDto(){
        return studentService.getStudentsDto();
    }
    @GetMapping(path = "myapi/students")
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    @PostMapping(path = "myapi/student")
    public Student newStudent(@RequestBody StudentDto studentDto){
        Student student = new Student();
        student.setStatus("Pending");
        student.setStudies(studentDto.getStudies());
        student.setDegree(studentDto.getDegree());
        student.setCourse(courseService.getCourse(studentDto.getCourse_id()).get());

        User user = userService.findByEmail(studentDto.getUser_email());
        student.setUser(user);
        return studentService.saveStudent(studentService.saveStudent(student));
    }
}
