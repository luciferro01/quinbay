package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.controllers;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.StudentService;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<CommonResponse<PageImpl<StudentDto>>> getAllStudents(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("GET /students");
        Pageable pageable = PageRequest.of(page, size);

        Page<StudentDto> dtos = studentService.getAllStudents(pageable);
        CommonResponse<PageImpl<StudentDto>> response = CommonResponse.success(new PageImpl<>(dtos.getContent(), pageable, dtos.getTotalPages()), 200, "Students fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<StudentDto>> getStudentById(@PathVariable("id") String id) {
        log.info("GET /students/{}", id);
        StudentDto dto = studentService.getStudentById(id);
        CommonResponse<StudentDto> response = CommonResponse.success(dto, 200, "Student fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerStudentToCourse")
    public ResponseEntity<CommonResponse<StudentDto>> registerStudentToCourse(@RequestParam("studentId") String studentId,
                                                                              @RequestParam("courseId") String courseId) {
        log.info("POST /students/registerStudentToCourse - Registering student to course");
        StudentDto dto = studentService.registerStudentToCourse(studentId, courseId);
        CommonResponse<StudentDto> response = CommonResponse.success(dto, 200, "Student registered to course successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deRegisterStudentFromCourse")
    public ResponseEntity<CommonResponse<StudentDto>> deRegisterStudentFromCourse(@RequestParam("studentId") String studentId,
                                                                                  @RequestParam("courseId") String courseId) {
        log.info("POST /students/deRegisterStudentFromCourse - De-registering student from course");
        StudentDto dto = studentService.deRegisterStudentFromCourse(studentId, courseId);
        CommonResponse<StudentDto> response = CommonResponse.success(dto, 200, "Student de-registered from course successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<StudentDto>> addStudent(@RequestParam("orgId") String orgId,
                                                                 @RequestBody StudentDto student) {
        log.info("POST /students - Adding student");
        StudentDto createdStudent = studentService.addStudent(student, orgId);
        CommonResponse<StudentDto> response = CommonResponse.success(createdStudent, 200, "Student added successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<StudentDto>> updateStudent(@PathVariable("id") String id,
                                                                    @RequestBody StudentDto student) {
        log.info("PUT /students/{} - Updating student", id);
        StudentDto updatedStudent = studentService.updateStudent(id, student);
        CommonResponse<StudentDto> response = CommonResponse.success(updatedStudent, 200, "Student updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteStudent(@PathVariable("id") String id) {
        log.info("DELETE /students/{} - Deleting student", id);
        studentService.deleteStudent(id);
        CommonResponse<Void> response = CommonResponse.success(null, 200, "Student deleted successfully");
        return ResponseEntity.ok(response);
    }
}