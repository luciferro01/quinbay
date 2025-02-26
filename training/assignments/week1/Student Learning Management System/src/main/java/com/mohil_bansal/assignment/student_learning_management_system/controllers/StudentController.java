package com.mohil_bansal.assignment.student_learning_management_system.controllers;

import com.mohil_bansal.assignment.student_learning_management_system.dto.StudentCourseStatusDto;
import com.mohil_bansal.assignment.student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.student_learning_management_system.services.StudentService;
import com.mohil_bansal.assignment.student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<StudentDto>>> getAllStudents() {
        log.info("GET /students - Fetching all students");
        List<StudentDto> students = studentService.getAllStudents();
        CommonResponse<List<StudentDto>> response = CommonResponse.success(students, HttpStatus.OK.value(), "Students fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<StudentDto>> getStudentById(@PathVariable("id") Long id) {
        log.info("GET /students/{} - Fetching student by ID", id);
        StudentDto student = studentService.getStudentById(id);
        CommonResponse<StudentDto> response = CommonResponse.success(student, HttpStatus.OK.value(), "Student fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/course-status/{status}")
    public ResponseEntity<CommonResponse<List<StudentCourseStatusDto>>> getStudentsByCourseStatus(
            @PathVariable("status") String status) {
        log.info("GET /api/students/course-status/{} - Fetching students by course status", status);
        List<StudentCourseStatusDto> students = studentService.getStudentsByCourseStatus(status);
        CommonResponse<List<StudentCourseStatusDto>> response = CommonResponse.success(
                students, HttpStatus.OK.value(), "Students fetched successfully by course status");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-course-status/{studentId}/{courseId}/{status}")
    public ResponseEntity<CommonResponse<StudentCourseStatusDto>> updateStudentCourseStatus(
            @PathVariable("studentId") Long studentId,
            @PathVariable("courseId") Long courseId,
            @PathVariable("status") String status) {
        log.info("POST /api/students/update-course-status/{}/{} - Updating student course status", studentId, courseId);
        StudentCourseStatusDto studentCourseStatus = studentService.updateStudentCourseStatus(studentId, courseId, status);
        CommonResponse<StudentCourseStatusDto> response = CommonResponse.success(
                studentCourseStatus, HttpStatus.OK.value(), "Student course status updated successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/getCourseStatuses")
    public ResponseEntity<CommonResponse<List<StudentCourseStatusDto>>> getStudentCoursesProgress(@PathVariable("id") String studentId) {
        log.info("GET /students/{}/courses - Fetching courses for student", studentId);
        List<StudentCourseStatusDto> studentCourses = studentService.getStudentCourseStatus(Long.parseLong(studentId));
        CommonResponse<List<StudentCourseStatusDto>> response = CommonResponse.success(studentCourses, HttpStatus.OK.value(), "Courses fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/getCourseStatus/{courseId}")
    public ResponseEntity<CommonResponse<StudentCourseStatusDto>> getStudentCourseProgress(@PathVariable("id") Long studentId,
                                                                                          @PathVariable("courseId") Long courseId) {
        log.info("GET /students/{}/courses/{} - Fetching course progress for student", studentId, courseId);
        StudentCourseStatusDto studentCourse = studentService.getStudentCourseProgress(studentId, courseId);
        CommonResponse<StudentCourseStatusDto> response = CommonResponse.success(studentCourse, HttpStatus.OK.value(), "Course progress fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{orgId}")
    public ResponseEntity<CommonResponse<StudentDto>> addStudent(@PathVariable("orgId") Long orgId,
                                                                 @RequestBody StudentDto studentDto) {
        log.info("POST /students/{} - Adding student to organization", orgId);
        StudentDto createdStudent = studentService.addStudent(studentDto, orgId);
        CommonResponse<StudentDto> response = CommonResponse.success(createdStudent, HttpStatus.CREATED.value(), "Student added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<StudentDto>> updateStudent(@PathVariable("id") Long id,
                                                                    @RequestBody StudentDto studentDto) {
        log.info("PUT /students/{} - Updating student", id);
        StudentDto updatedStudent = studentService.updateStudent(id, studentDto);
        CommonResponse<StudentDto> response = CommonResponse.success(updatedStudent, HttpStatus.OK.value(), "Student updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteStudent(@PathVariable("id") Long id) {
        log.info("DELETE /students/{} - Deleting student", id);
        studentService.deleteStudent(id);
        CommonResponse<Void> response = CommonResponse.success(null, HttpStatus.OK.value(), "Student deleted successfully");
        return ResponseEntity.ok(response);
    }
}