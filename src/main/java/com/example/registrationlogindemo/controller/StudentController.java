package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.StudentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.dto.UserDto2;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.service.impl.CourseService;
import com.example.registrationlogindemo.service.impl.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<StudentDto> getStudents(){
        List<Student> students = studentService.geStudentsWithStatusPending();
        return students.stream().map((student) -> convertEntityToDto(student))
                .collect(Collectors.toList());
    }



    private StudentDto convertEntityToDto(Student student){
        StudentDto studentDto = new StudentDto();
        studentDto.setStudies(student.getStudies());
        studentDto.setNationality(student.getNationality());
        studentDto.setAddress(student.getAddress());
        studentDto.setBirth(student.getBirth());
        studentDto.setPhone(student.getPhone());
        studentDto.setWhy(student.getWhy());
        studentDto.setEmail(student.getUser().getEmail());
        studentDto.setCourse_title(student.getCourse().getTitle());
        studentDto.setStatus(student.getStatus());
        return studentDto;
    }
    @GetMapping(path = "myapi/student")
    public Student getStudent(){
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return studentService.getStudentByUserId(user.getId());
    }

    @GetMapping(path = "myapi/has_applied")
    public Boolean hasApplied(){
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(studentService.findByUserId(user.getId()) != null){
            return true;
        }else {
            return false;
        }
    }

    @PostMapping(path = "myapi/student/approve")
    public Student approve(@RequestBody UserDto2 userDto2){
        User existing = userService.findByEmail(userDto2.getEmail());
        if ( existing == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email: "+ userDto2.getEmail() +" doesnt exists!");
        }
        Student student = studentService.findByEmail(userDto2.getEmail());
        student.setStatus("Approved");
        return studentService.saveStudent(student);
    }

    @PostMapping(path = "myapi/student/disapprove")
    public Student disapprove(@RequestBody UserDto2 userDto2){
        User existing = userService.findByEmail(userDto2.getEmail());
        if ( existing == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with email: "+ userDto2.getEmail() +" doesnt exists!");
        }
        Student student = studentService.findByEmail(userDto2.getEmail());
        student.setStatus("Disapprove");
        return studentService.saveStudent(student);
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
