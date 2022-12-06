package com.example.registrationlogindemo.service.impl;

import com.example.registrationlogindemo.entity.Course;
import com.example.registrationlogindemo.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses(){return courseRepository.findAll();}

    public Course getCourse(String title){
        return courseRepository.findByTitle(title);
    }

    public Course saveCourse(Course course) {
        courseRepository.save(course);
        return course;
    }

    public Course getCourseByTitle(String title) {
        return courseRepository.findByTitle(title);
    }

    public Course deleteCourse(Course course){
        courseRepository.delete(course);
        return course;
    }
}
