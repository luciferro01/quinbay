package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.controllers;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.InstructorDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.InstructorService;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    //    @GetMapping
//    public ResponseEntity<CommonResponse<List<InstructorDto>>> getAllInstructors() {
//        log.info("GET /instructors");
//        List<InstructorDto> dtos = instructorService.getAllInstructors();
//        CommonResponse<List<InstructorDto>> response = CommonResponse.success(dtos, 200, "Instructors fetched successfully");
//        return ResponseEntity.ok(response);
//    }
    @GetMapping
    public ResponseEntity<CommonResponse<PageImpl<InstructorDto>>> getAllInstructors(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("GET /instructors");
        Pageable pageable = PageRequest.of(page, size);
        Page<InstructorDto> dtos = instructorService.getAllInstructors(pageable);
        CommonResponse<PageImpl<InstructorDto>> response = CommonResponse.success(new PageImpl<>(dtos.getContent(), pageable, dtos.getTotalPages()), 200, "Instructors fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<InstructorDto>> getInstructorById(@PathVariable("id") String id) {
        log.info("GET /instructors/{}", id);
        InstructorDto dto = instructorService.findInstructorById(id);
        CommonResponse<InstructorDto> response = CommonResponse.success(dto, 200, "Instructor fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerInstructorToCourse")
    public ResponseEntity<CommonResponse<InstructorDto>> registerInstructorToCourse(@RequestParam("instructorId") String instructorId,
                                                                                    @RequestParam("courseId") String courseId) {
        log.info("POST /instructors/registerInstructorToCourse - Registering instructor to course");
        InstructorDto dto = instructorService.registerInstructorToCourse(instructorId, courseId);
        CommonResponse<InstructorDto> response = CommonResponse.success(dto, 200, "Instructor registered to course successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deRegisterInstructorFromCourse")
    public ResponseEntity<CommonResponse<InstructorDto>> deRegisterInstructorFromCourse(@RequestParam("instructorId") String instructorId) {
        log.info("POST /instructors/deRegisterInstructorFromCourse - De-registering instructor from course");
        InstructorDto dto = instructorService.deRegisterInstructorFromCourse(instructorId);
        CommonResponse<InstructorDto> response = CommonResponse.success(dto, 200, "Instructor de-registered from course successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<InstructorDto>> addInstructor(@RequestParam("orgId") String orgId,
                                                                       @RequestBody InstructorDto instructor) {
        log.info("POST /instructors - Adding instructor");
        InstructorDto createdInstructor = instructorService.addInstructor(instructor, orgId);
        CommonResponse<InstructorDto> response = CommonResponse.success(createdInstructor, 200, "Instructor added successfully");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<InstructorDto>> updateInstructor(@PathVariable("id") String id,
                                                                          @RequestBody InstructorDto instructor) {
        log.info("PATCH /instructors/{} - Updating instructor", id);
        InstructorDto updatedInstructor = instructorService.updateInstructor(id, instructor);
        CommonResponse<InstructorDto> response = CommonResponse.success(updatedInstructor, 200, "Instructor updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteInstructor(@PathVariable("id") String id) {
        log.info("DELETE /instructors/{} - Deleting instructor", id);
        instructorService.deleteInstructor(id);
        CommonResponse<Void> response = CommonResponse.success(null, 200, "Instructor deleted successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateCourseStatus/{studentId}/{courseId}/{courseStatus}")
    public ResponseEntity<CommonResponse<StudentDto>> updateCourseStatus(
            @PathVariable("studentId") String studentId,
            @PathVariable("courseId") String courseId, @PathVariable("courseStatus") String courseStatus) {
        log.info("POST /instructors/updateCourseStatus - Updating course status for student {} in course {} to {}", studentId, courseId, courseStatus);
        CourseStatus status = CourseStatus.valueOf(courseStatus.toUpperCase());
        StudentDto updatedCourseStatus = instructorService.updateCourseStatus(studentId, courseId, status);
        CommonResponse<StudentDto> response = CommonResponse.success(updatedCourseStatus, 200, "Course status updated successfully");
        return ResponseEntity.ok(response);
    }
}