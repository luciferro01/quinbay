package com.mohil_bansal.assignment.student_learning_management_system.controllers;

import com.mohil_bansal.assignment.student_learning_management_system.dto.InstructorDto;
import com.mohil_bansal.assignment.student_learning_management_system.services.InstructorService;
import com.mohil_bansal.assignment.student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<InstructorDto>>> getAllInstructors() {
        log.info("GET /instructors");
        List<InstructorDto> instructors = instructorService.getAllInstructors();
        CommonResponse<List<InstructorDto>> response = CommonResponse.success(instructors, 200, "Instructors fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<InstructorDto>> getInstructorById(@PathVariable("id") Long id) {
        log.info("GET /instructors/{}", id);
        InstructorDto instructor = instructorService.findInstructorById(id);
        CommonResponse<InstructorDto> response = CommonResponse.success(instructor, 200, "Instructor fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerInstructorToCourse")
    public ResponseEntity<CommonResponse<InstructorDto>> registerInstructorToCourse(@RequestParam("instructorId") Long instructorId,
                                                                                    @RequestParam("courseId") Long courseId) {
        log.info("POST /instructors/registerInstructorToCourse - Registering instructor to course");
        InstructorDto instructor = instructorService.registerInstructorToCourse(instructorId, courseId);
        CommonResponse<InstructorDto> response = CommonResponse.success(instructor, 200, "Instructor registered to course successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deRegisterInstructorFromCourse")
    public ResponseEntity<CommonResponse<InstructorDto>> deRegisterInstructorFromCourse(@RequestParam Long instructorId) {
        log.info("POST /instructors/deRegisterInstructorFromCourse - DeRegistering instructor from course");
        InstructorDto instructor = instructorService.deRegisterInstructorFromCourse(instructorId);
        CommonResponse<InstructorDto> response = CommonResponse.success(instructor, 200, "Instructor de-registered from course successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<InstructorDto>> addInstructor(@RequestParam("orgId") Long orgId,
                                                                       @RequestBody InstructorDto instructorDto) {
        log.info("POST /instructors - Adding instructor");
        InstructorDto instructor = instructorService.addInstructor(instructorDto, orgId);
        CommonResponse<InstructorDto> response = CommonResponse.success(instructor, 200, "Instructor added successfully");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<InstructorDto>> updateInstructor(@PathVariable("id") Long id,
                                                                          @RequestBody InstructorDto instructorDto) {
        log.info("PATCH /instructors/{} - Updating instructor", id);
        InstructorDto instructor = instructorService.updateInstructor(id, instructorDto);
        CommonResponse<InstructorDto> response = CommonResponse.success(instructor, 200, "Instructor updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteInstructor(@PathVariable("id") Long id) {
        log.info("DELETE /instructors/{} - Deleting instructor", id);
        instructorService.deleteInstructor(id);
        CommonResponse<Void> response = CommonResponse.success(null, 200, "Instructor deleted successfully");
        return ResponseEntity.ok(response);
    }
}