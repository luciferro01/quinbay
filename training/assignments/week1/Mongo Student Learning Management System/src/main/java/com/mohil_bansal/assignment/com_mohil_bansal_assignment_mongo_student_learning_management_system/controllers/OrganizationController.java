package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.controllers;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseDetailsDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseEnrollmentStatsDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.InstructorDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.OrganizationDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.OrganizationService;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<OrganizationDto>>> getAllOrganizations() {
        List<OrganizationDto> dtos = organizationService.getAllOrganizations();
        CommonResponse<List<OrganizationDto>> response = CommonResponse.success(dtos, 200, "Organizations fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<OrganizationDto>> getOrganizationById(@PathVariable("id") String id) {
        OrganizationDto dto = organizationService.getOrganizationById(id);
        CommonResponse<OrganizationDto> response = CommonResponse.success(dto, 200, "Organization fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<OrganizationDto>> addOrganization(@RequestBody OrganizationDto organizationDto) {
        OrganizationDto createdOrg = organizationService.addOrganization(organizationDto);
        CommonResponse<OrganizationDto> response = CommonResponse.success(createdOrg, 200, "Organization added successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<OrganizationDto>> updateOrganization(@PathVariable("id") String id,
                                                                              @RequestBody OrganizationDto organizationDto) {
        OrganizationDto updatedOrg = organizationService.updateOrganization(id, organizationDto);
        CommonResponse<OrganizationDto> response = CommonResponse.success(updatedOrg, 200, "Organization updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteOrganization(@PathVariable("id") String id) {
        organizationService.deleteOrganization(id);
        CommonResponse<Void> response = CommonResponse.success(null, 200, "Organization deleted successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orgId}/student-count")
    public ResponseEntity<CommonResponse<Long>> getStudentCount(@PathVariable String orgId) {
        long count = organizationService.getStudentCount(orgId);
        return ResponseEntity.ok(CommonResponse.success(count, 200, "Student count fetched successfully"));
    }

    @GetMapping("/{orgId}/course-enrollment")
    public ResponseEntity<CommonResponse<List<CourseEnrollmentStatsDto>>> getStudentCountByCourse(
            @PathVariable String orgId) {
        List<CourseEnrollmentStatsDto> counts = organizationService.getStudentCountByCourse(orgId);
        return ResponseEntity.ok(CommonResponse.success(counts, 200, "Course enrollment counts fetched successfully"));
    }

    @GetMapping("/courses/{courseId}/instructors")
    public ResponseEntity<CommonResponse<List<InstructorDto>>> getCourseInstructors(
            @PathVariable String courseId) {
        List<InstructorDto> dtos = organizationService.getInstructorsByCourse(courseId);
        return ResponseEntity.ok(CommonResponse.success(dtos, 200, "Course instructors fetched successfully"));
    }

    @GetMapping("/{orgId}/instructor-count")
    public ResponseEntity<CommonResponse<Long>> getInstructorCount(@PathVariable String orgId) {
        long count = organizationService.getInstructorCount(orgId);
        return ResponseEntity.ok(CommonResponse.success(count, 200, "Instructor count fetched successfully"));
    }

    @GetMapping("/courses/{courseId}/details")
    public ResponseEntity<CommonResponse<CourseDetailsDto>> getCourseDetails(
            @PathVariable String courseId) {
        CourseDetailsDto dto = organizationService.getCourseDetailsById(courseId);
        return ResponseEntity.ok(CommonResponse.success(dto, 200, "Course details fetched successfully"));
    }

    @GetMapping("/students/by-status")
    public ResponseEntity<CommonResponse<List<StudentDto>>> getStudentsByCourseStatus(
            @RequestParam CourseStatus status) {
        List<StudentDto> dtos = organizationService.getStudentsByCourseStatus(status);
        return ResponseEntity.ok(CommonResponse.success(dtos, 200, "Students fetched successfully"));
    }
}