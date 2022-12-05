package com.example.registrationlogindemo.controller;

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
        if (courseService.getCourseByName(courseDto.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course with name: "+ courseDto.getName() +" already exists!");
        }
        Course course = new Course();
        course.setDetails(courseDto.getDetails());
        course.setName(courseDto.getName());
        return courseService.saveCourse(course);
    }
}