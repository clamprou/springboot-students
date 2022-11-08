package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.StudentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.CourseRepository;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.service.impl.CourseService;
import com.example.registrationlogindemo.service.impl.StudentService;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private UserService userService;
    private StudentService studentService;

    private RoleRepository roleRepository;

    private CourseService courseService;

    public AuthController(UserService userService, StudentService studentService, RoleRepository roleRepository, CourseRepository courseRepository, CourseService courseService) {
        this.userService = userService;
        this.studentService = studentService;
        this.roleRepository = roleRepository;
        this.courseService = courseService;
    }

    @GetMapping("index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle register user form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model){
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/apply";
    }

    @PostMapping("/apply/save")
    public String makeApply(@Valid @ModelAttribute("student") Student student, BindingResult result, Model model){
        System.out.println(student.getCourse().getName());
        return "index";
//        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        if(user != null){
//            Student student = new Student();
//            student.setDegree(studentDto.getDegree());
//            student.setUser(user);
//            studentService.saveStudent(student);
//            Role role = roleRepository.findByName("ROLE_STUDENT");
//            if(role == null){
//                role = checkRoleExist("ROLE_STUDENT");
//            }
//            user.getRoles().add(role);
//            userService.saveUser(user);
//            return "succesapply";
//        }
//        return "/login";
    }

    @GetMapping("/myapply")
    public String showMyApply(Model model){
//        Apply apply = null;
//        model.addAttribute("apply", apply);
        return "myapply";
    }

    @GetMapping("/apply")
    public String showApplyForm(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        model.addAttribute("courses", courseService.getCourses());
        return "apply";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    private Role checkRoleExist(String role) {
        Role rolee = new Role();
        rolee.setName(role);
        return roleRepository.save(rolee);
    }
}
