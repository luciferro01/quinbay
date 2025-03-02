package com.mohil_bansal.assignment.student_learning_management_system.services;

import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseDto;

import java.util.List;

public interface CourseService {
    List<CourseDto> getAllCourses();
    CourseDto getCourseById(Long id);
    CourseDto addCourse(CourseDto courseDto, Long orgId);
    CourseDto updateCourse(Long id, CourseDto courseDto);
    void deleteCourse(Long id);
}