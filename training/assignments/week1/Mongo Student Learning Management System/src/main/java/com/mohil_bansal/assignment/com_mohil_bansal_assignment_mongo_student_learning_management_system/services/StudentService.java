
package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.StudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(String studentId);

    StudentDto registerStudentToCourse(String studentId, String courseId);

    StudentDto deRegisterStudentFromCourse(String studentId, String courseId);

    StudentDto addStudent(StudentDto student, String organizationId);

    StudentDto updateStudent(String studentId, StudentDto updatedStudent);

    void deleteStudent(String studentId);
}