
package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {

    Page<StudentDto> getAllStudents(Pageable pageable);

    StudentDto getStudentById(String studentId);

    StudentDto registerStudentToCourse(String studentId, String courseId);

    StudentDto deRegisterStudentFromCourse(String studentId, String courseId);

    StudentDto addStudent(StudentDto student, String organizationId);

    StudentDto updateStudent(String studentId, StudentDto updatedStudent);

    void deleteStudent(String studentId);
}