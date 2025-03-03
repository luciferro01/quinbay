package com.mohil_bansal.assignment.student_learning_management_system.services;

import com.mohil_bansal.assignment.student_learning_management_system.dto.RegistrationDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.StudentCourse;

public interface RegistrationService {
//    RegistrationDto registerStudentToCourse(RegistrationDto registrationDto);

    StudentCourse registerStudentToCourse(Long studentId, Long courseId, String courseStatus);

    void withdrawStudentFromCourse(Long studentId, Long courseId);

    RegistrationDto updateCourseStatus(RegistrationDto registrationDto);
}