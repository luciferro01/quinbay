package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    //    List<CourseDto> getAllCourses();
    Page<CourseDto> getAllCourses(Pageable pageable);

    CourseDto getCourseById(String courseId);

    CourseDto addCourse(CourseDto course, String organizationId);

    void deleteCourse(String courseId);
}