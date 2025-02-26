
package com.mohil_bansal.assignment.student_learning_management_system.services;

import com.mohil_bansal.assignment.student_learning_management_system.dto.StudentCourseStatusDto;
import com.mohil_bansal.assignment.student_learning_management_system.dto.StudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(Long studentId);

    List<StudentCourseStatusDto> getStudentCourseStatus(Long studentId);

    StudentCourseStatusDto getStudentCourseProgress(Long studentId, Long courseId);

    StudentDto addStudent(StudentDto studentDto, Long organizationId);

    StudentDto updateStudent(Long studentId, StudentDto studentDto);

    void deleteStudent(Long studentId);

    List<StudentCourseStatusDto> getStudentsByCourseStatus(String courseStatus);

    StudentCourseStatusDto updateStudentCourseStatus(Long studentId, Long courseId, String courseStatus);
}