package com.mohil_bansal.day1.day1.controller;

import com.mohil_bansal.day1.day1.DTO.CourseDTO;
import com.mohil_bansal.day1.day1.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping("/getCourseById")
    public String getCourseById() {
        return courseService.getAllCourses();
    }


    @PostMapping("/createCourse")
    public CourseDTO createCourse(@RequestBody CourseDTO courseDTO) {
        return courseService.createCourse(courseDTO);
    }
}
