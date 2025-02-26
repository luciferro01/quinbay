package com.mohil_bansal.assignment.student_learning_management_system.services;

import com.mohil_bansal.assignment.student_learning_management_system.entity.StudentCourse;

public interface RegistrationService {

    StudentCourse registerStudentToCourse(Long studentId, Long courseId, String courseStatus);

    void withdrawStudentFromCourse(Long studentId, Long courseId);

    StudentCourse updateCourseStatus(Long studentId, Long courseId, String newStatus);
}