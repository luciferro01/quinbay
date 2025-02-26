package com.mohil_bansal.assignment.student_learning_management_system.services;

import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseDetailsDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> getAllCourses();

    Course getCourseById(Long courseId);

    Course addCourse(Course course, Long organizationId);

    void deleteCourse(Long courseId);

    CourseDetailsDto getCourseDetailsById(Long courseId);

}