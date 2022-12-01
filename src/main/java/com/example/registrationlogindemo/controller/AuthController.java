package com.example.registrationlogindemo.controller;

import com.example.registrationlogindemo.dto.StudentDto;
import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.security.CustomUserDetailsService;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.service.impl.CourseService;
import com.example.registrationlogindemo.service.impl.SecretaryService;
import com.example.registrationlogindemo.service.impl.StudentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    private UserService userService;
    private StudentService studentService;

    private RoleRepository roleRepository;

    private CourseService courseService;

    private SecretaryService secretaryService;


    public AuthController(UserService userService, StudentService studentService, RoleRepository roleRepository, CourseService courseService, CustomUserDetailsService customUserDetailsService, SecretaryService secretaryService) {
        this.userService = userService;
        this.studentService = studentService;
        this.roleRepository = roleRepository;
        this.courseService = courseService;
        this.secretaryService = secretaryService;
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
    @GetMapping("/register")
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
//        SecurityContextHolder.getContext().setAuthentication(Authentication);
        return "redirect:/register?success";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }

    @PostMapping("/apply/save")
    public String makeApply(@Valid @ModelAttribute("student") Student student, BindingResult result, Model model){
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user != null){
            student.setStatus("Pending");
            student.setUser(user);
            studentService.saveStudent(student);
            Role role = roleRepository.findByName("ROLE_STUDENT");
            if(role == null){
                role = checkRoleExist("ROLE_STUDENT");
            }
            user.getRoles().add(role);
            userService.saveUser(user);
            return "succesapply";
        }
        return "index";
    }

    @GetMapping("/myapply")
    public String showMyApply(Model model){
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(user != null){
            Student student = studentService.getStudentByUserId(user.getId());
            model.addAttribute("user",user);
            model.addAttribute("student",student);
        }
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

    @GetMapping("/applies")
    public String getApplies(Model model){
        List<Student> students = studentService.geStudentsWithStatusPending();
        List<User> users = new ArrayList<>();
        for (Student student :
                students) {
            users.add(student.getUser());
        }
        User user = new User();
        Student student = new Student();
        student.setStatus("Approved");
        user.setStudent(student);
        model.addAttribute("users", users);
        model.addAttribute("user", user);
        return "applies";
    }
    @PostMapping("/applyresault")
    public String submitApply(User user,Model model){
        Optional<User> user1 = userService.findById(user.getId());
        if(user1.isPresent()){
            user1.get().getStudent().setStatus(user.getStudent().getStatus());
            userService.saveUser(user1.get());

            return "redirect:/applies";
        }
        return "index";
    }
    @GetMapping("/default")
    public String defaultPage(Model model) {
        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(null,"ROLE_STUDENT",null));
        roles.add(new Role(null,"ROLE_SECRETARY",null));
        roles.add(new Role(null,"ROLE_ADMIN",null));
        if (user == null) return "index";
        //Student
        if (user.getRoles().contains(new Role(null,"ROLE_STUDENT",null))) {
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return "default";
        //Secretary
        } else if (user.getRoles().contains(new Role(null,"ROLE_SECRETARY",null))) {
            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            return "default";
        //Admin
        } else if (user.getRoles().contains(new Role(null,"ROLE_ADMIN",null))) {
            model.addAttribute("roles", roles);
            return "redirect:/users";
        }else if (user.getRoles().contains(new Role(null,"ROLE_USER",null))){
            return "redirect:/apply";
        }
        return "index";
    }

//    @PostMapping("/")
//    public String defaultPagee(Model model) {
//        User user = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//        if (user == null) return "index";
//        if (user.getRoles().size() == 1) return "redirect:/apply";
//        if (user.getRoles().size() >= 2) {
//            if ("ROLE_STUDENT".equals(user.getRoles().get(1))) {
//                model.addAttribute("user", user);
//                return "default";
//            } else if ("ROLE_SECRETARY".equals(user.getRoles().get(1))) {
//                model.addAttribute("user", user);
//                return "default";
//            } else if ("ROLE_ADMIN".equals(user.getRoles().get(1))) {
//                return "redirect:/users";
//            }
//        }
//        return "index";
//    }

    private Role checkRoleExist(String role) {
        Role rolee = new Role();
        rolee.setName(role);
        return roleRepository.save(rolee);
    }
}
