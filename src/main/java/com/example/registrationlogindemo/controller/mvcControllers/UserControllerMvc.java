package com.example.registrationlogindemo.controller.mvcControllers;

import com.example.registrationlogindemo.dto.UserDto;
import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.entity.Secretary;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.service.impl.CourseService;
import com.example.registrationlogindemo.service.impl.SecretaryService;
import com.example.registrationlogindemo.service.impl.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserControllerMvc {
    private UserService userService;
    private StudentService studentService;

    private RoleRepository roleRepository;

    private CourseService courseService;

    private SecretaryService secretaryService;

    private PasswordEncoder passwordEncoder;
    public UserControllerMvc(UserService userService, StudentService studentService, RoleRepository roleRepository, CourseService courseService, SecretaryService secretaryService){

        this.userService = userService;
        this.studentService = studentService;
        this.roleRepository = roleRepository;
        this.courseService = courseService;
        this.secretaryService = secretaryService;
    }
    @GetMapping("/users")
    public String listRegisteredUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new UserDto());
        return "users";
    }

    @GetMapping("/activated")
    public String getActivatedUsers(Model model){
        List<UserDto> users = userService.findAllUsersActivated();
        model.addAttribute("users", users);
        model.addAttribute("user", new UserDto());
        return "activated";
    }

    @GetMapping("/roles")
    public String getNotActivatedUsers(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new UserDto());
        return "roles";
    }

    @RequestMapping(value = "/activate_student", method = RequestMethod.GET)
    public String activateStudent(@RequestParam(name="email")String email, RedirectAttributes redirectAttributes){
        User existing = userService.findByEmail(email);
//        if (existing == null) {
//            result.rejectValue("email", null, "There is not an account registered with that email");
//        }
//        if (result.hasErrors()) {
//            return "redirect:/not_activated";
//        }
        existing.setActivated(true);
        Role role = roleRepository.findByName("ROLE_STUDENT");
        if(role == null){
            role = checkRoleExist("ROLE_STUDENT");
        }
        List<Role> roles = existing.getRoles();
        if(!roles.contains(role)){
            roles.add(role);
            existing.setRoles(roles);
        }
        if(roles.contains(roleRepository.findByName("ROLE_SECRETARY"))){
            roles.remove(roleRepository.findByName("ROLE_SECRETARY"));
            existing.setRoles(roles);
        }
        userService.saveUser(existing);
        return "redirect:/roles";
    }

    @RequestMapping(value = "/activate_secretary", method = RequestMethod.GET)
    public String activateSecretary(@RequestParam(name="email")String email, RedirectAttributes redirectAttributes){
        User existing = userService.findByEmail(email);
//        if (existing == null) {
//            result.rejectValue("email", null, "There is not an account registered with that email");
//        }
//        if (result.hasErrors()) {
//            return "redirect:/not_activated";
//        }
        existing.setActivated(true);
        Role role = roleRepository.findByName("ROLE_SECRETARY");
        if(role == null){
            role = checkRoleExist("ROLE_SECRETARY");
        }
        List<Role> roles = existing.getRoles();
        if(!roles.contains(role)){
            roles.add(role);
            existing.setRoles(roles);
        }
        if(roles.contains(roleRepository.findByName("ROLE_STUDENT"))){
            roles.remove(roleRepository.findByName("ROLE_STUDENT"));
            existing.setRoles(roles);
        }
        userService.saveUser(existing);
        return "redirect:/roles";
    }

    @PostMapping("/edit_user")
    public String editUser(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing == null) {
            result.rejectValue("email", null, "There is not an account registered with that email");
        }
        if (result.hasErrors()) {
            return "redirect:/users";
        }
        existing.setName(user.getFirstName() + " " + user.getLastName());
        existing.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(existing);
//        SecurityContextHolder.getContext().setAuthentication(Authentication);
        return "redirect:/users";
    }

    @PostMapping("/save_user")
    public String submitForm(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            return "redirect:/users";
        }
        userService.saveUser(user);
//        SecurityContextHolder.getContext().setAuthentication(Authentication);
        return "redirect:/users";
    }

    @RequestMapping(value = "/delete_user", method = RequestMethod.GET)
    public String handleDeleteUser(@RequestParam(name="email")String email, RedirectAttributes redirectAttributes) {
        Student student = studentService.findByEmail(email);
        Secretary secretary = secretaryService.findByEmail(email);
        User user = userService.findByEmail(email);
        Role role = roleRepository.findByName("ROLE_USER");
        if(role == null){
            role = checkRoleExist("ROLE_USER");
        }
        user.getRoles().remove(role);
        userService.saveUser(user);
        if(user == null) return "error";
        if(student != null){//if student
            role = roleRepository.findByName("ROLE_STUDENT");
            if(role == null){
                role = checkRoleExist("ROLE_STUDENT");
            }
            user.getRoles().remove(role);
            userService.saveUser(user);
            studentService.deleteStudent(student);
        }else if (secretary != null){
            role = roleRepository.findByName("ROLE_SECRETARY");
            if(role == null){
                role = checkRoleExist("ROLE_SECRETARY");
            }
            user.getRoles().remove(role);
            userService.saveUser(user);
            secretaryService.deleteSecretary(secretary);
        } else {//admin
            role = roleRepository.findByName("ROLE_ADMIN");
            if(role == null){
                role = checkRoleExist("ROLE_ADMIN");
            }
            user.getRoles().remove(role);

            userService.saveUser(user);
            userService.deleteUser(user);
        }
        userService.deleteUser(user);

        redirectAttributes.addFlashAttribute("message", "Successful!");
        return "redirect:/users";
    }

    private Role checkRoleExist(String role) {
        Role rolee = new Role();
        rolee.setName(role);
        return roleRepository.save(rolee);
    }
}
