package com.example.registrationlogindemo.controller.ApiControllers;

import com.example.registrationlogindemo.dto.CourseDto;
import com.example.registrationlogindemo.entity.Course;
import com.example.registrationlogindemo.service.impl.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(path = "myapi/courses")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @PostMapping(path = "myapi/course")
    public Course newCourse(@RequestBody CourseDto courseDto) {
        if (courseService.getCourseByTitle(courseDto.getTitle()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course with title: "+ courseDto.getTitle() +" already exists!");
        }
        Course course = new Course();
        course.setDetails(courseDto.getDetails());
        course.setTitle(courseDto.getTitle());
        return courseService.saveCourse(course);
    }
    @PutMapping(path = "myapi/course")
    public Course editCourse(@RequestBody CourseDto courseDto) {
        if (courseService.getCourseByTitle(courseDto.getTitle()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course with title: "+ courseDto.getTitle() +" doesnt exists!");
        }
        Course course = courseService.getCourseByTitle(courseDto.getTitle());
        course.setDetails(courseDto.getDetails());
        return courseService.saveCourse(course);
    }

    @DeleteMapping(path = "myapi/course/{title}")
    public Course deleteCourse(@PathVariable String title){
        if (courseService.getCourseByTitle(title) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course with title: "+ title +" doesnt exists!");
        }
        return courseService.deleteCourse(courseService.getCourseByTitle(title));
    }
}