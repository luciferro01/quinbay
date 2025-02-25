package com.mohil_bansal.day1.day1.service;

import com.mohil_bansal.day1.day1.DTO.CourseDTO;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {
    CourseDTO createCourse(CourseDTO courseDTO);
    String updateCourse(CourseDTO courseDTO);
    String deleteCourse(Long id);
    CourseDTO getCourseById(Long id);
    String getAllCourses();
}
