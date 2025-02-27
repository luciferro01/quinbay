package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseEnrollmentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseEnrollmentStatsDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Instructor;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Student;

import java.util.List;
import java.util.Map;

public interface OrganizationRepositoryCustom {
    long getStudentCount(String orgId);
//    Map<String, Long> getStudentCountByCourse(String orgId);
    List<CourseEnrollmentStatsDto> getStudentCountByCourse(String orgId);
    List<Instructor> getInstructorsByCourse(String courseId);
    long getInstructorCount(String orgId);
    Map<String, Object> getCourseDetailsById(String courseId);
    List<Student> getStudentsByCourseStatus(CourseStatus status);
}
