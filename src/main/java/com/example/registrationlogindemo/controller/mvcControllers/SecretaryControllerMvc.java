package com.example.registrationlogindemo.controller.mvcControllers;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Course;
import com.example.registrationlogindemo.entity.Secretary;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.service.impl.CourseService;
import com.example.registrationlogindemo.service.impl.SecretaryService;
import com.example.registrationlogindemo.service.impl.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SecretaryControllerMvc {
    private UserService userService;
    private StudentService studentService;

    private RoleRepository roleRepository;

    private CourseService courseService;

    private SecretaryService secretaryService;
    public SecretaryControllerMvc(UserService userService, StudentService studentService, RoleRepository roleRepository, CourseService courseService){
        this.userService = userService;
        this.studentService = studentService;
        this.roleRepository = roleRepository;
        this.courseService = courseService;
    }
    @GetMapping("/secretaries")
    public String listCourses(Model model){
        List<UserDto> users = userService.findAllUsersNotActivated();
        model.addAttribute("users", users);
        model.addAttribute("user", new UserDto());
        model.addAttribute("secretary", new Secretary());
        return "secretaries";
    }
}
