package com.example.registrationlogindemo.controller.mvcControllers;

import com.example.registrationlogindemo.entity.Course;
import com.example.registrationlogindemo.entity.Role;
import com.example.registrationlogindemo.entity.Student;
import com.example.registrationlogindemo.entity.User;
import com.example.registrationlogindemo.repository.RoleRepository;
import com.example.registrationlogindemo.service.UserService;
import com.example.registrationlogindemo.service.impl.CourseService;
import com.example.registrationlogindemo.service.impl.SecretaryService;
import com.example.registrationlogindemo.service.impl.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CourseControllerMvc {
    private UserService userService;
    private StudentService studentService;

    private RoleRepository roleRepository;

    private CourseService courseService;

    private SecretaryService secretaryService;

    public CourseControllerMvc(UserService userService, StudentService studentService, RoleRepository roleRepository, CourseService courseService, SecretaryService secretaryService){

        this.userService = userService;
        this.studentService = studentService;
        this.roleRepository = roleRepository;
        this.courseService = courseService;
        this.secretaryService = secretaryService;
    }
    @GetMapping("/courses")
    public String listCourses(Model model){
        List<Course> courses = courseService.getCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("course", new Course());
        return "courses";
    }

    @RequestMapping(value = "/delete_course", method = RequestMethod.GET)
    public String deleteCourse(@RequestParam(name="title")String title, RedirectAttributes redirectAttributes) {
        Course existing = courseService.getCourseByTitle(title);
        if (existing == null) {
            return "redirect:/courses";
        }
        //delete students with this course
        List<Student> students = studentService.getStudentsWithCourse(title);
        if (!students.isEmpty()){
            Role role = roleRepository.findByName("ROLE_STUDENT");
            if(role == null){
                role = checkRoleExist("ROLE_STUDENT");
            }
            students.stream().forEach((student) -> {
                User user = student.getUser();
                userService.findByEmail(user.getEmail()).getRoles().remove(roleRepository.findByName("ROLE_STUDENT"));
                userService.saveUser(user);
                studentService.deleteStudent(student);
            });
        }
        courseService.deleteCourse(existing);
        return "redirect:/courses";
    }

    @PostMapping("/save_course")
    public String saveCourse(@Valid @ModelAttribute("course") Course course, BindingResult result, Model model) {
        Course existing = courseService.getCourseByTitle(course.getTitle());
        if (existing != null) {
            result.rejectValue("Title", null, "There is already a Course registered with that Title");
        }
        if (result.hasErrors()) {
            return "redirect:/courses";
        }
        courseService.saveCourse(course);
//        SecurityContextHolder.getContext().setAuthentication(Authentication);
        return "redirect:/courses";
    }

    @PostMapping("/edit_course")
    public String editCourse(@Valid @ModelAttribute("course") Course course, BindingResult result, Model model) {
        Course existing = courseService.getCourseByTitle(course.getTitle());
        if (existing == null) {
            result.rejectValue("Title", null, "There is not an account registered with that Title");
        }
        if (result.hasErrors()) {
            return "redirect:/courses";
        }
        existing.setDetails(course.getDetails());
        courseService.saveCourse(existing);
        return "redirect:/courses";
    }

    private Role checkRoleExist(String role) {
        Role rolee = new Role();
        rolee.setName(role);
        return roleRepository.save(rolee);
    }
}
