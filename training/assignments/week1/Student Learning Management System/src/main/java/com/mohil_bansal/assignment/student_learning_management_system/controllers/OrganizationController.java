
package com.mohil_bansal.assignment.student_learning_management_system.controllers;

import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseStudentCountDto;
import com.mohil_bansal.assignment.student_learning_management_system.dto.OrganizationDto;
import com.mohil_bansal.assignment.student_learning_management_system.services.OrganizationService;
import com.mohil_bansal.assignment.student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        log.info("GET /organizations - Fetching all organizations");
        List<OrganizationDto> organizations = organizationService.getAllOrganizations();
        CommonResponse<List<OrganizationDto>> response = CommonResponse.success(
                organizations, HttpStatus.OK.value(), "Organizations fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<OrganizationDto>> getOrganizationById(@PathVariable("id") Long id) {
        log.info("GET /organizations/{} - Fetching organization by ID", id);
        OrganizationDto organization = organizationService.getOrganizationById(id);
        CommonResponse<OrganizationDto> response = CommonResponse.success(
                organization, HttpStatus.OK.value(), "Organization fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<OrganizationDto>> addOrganization(@RequestBody OrganizationDto organizationDto) {
        log.info("POST /organizations - Adding organization");
        OrganizationDto createdOrg = organizationService.addOrganization(organizationDto);
        CommonResponse<OrganizationDto> response = CommonResponse.success(
                createdOrg, HttpStatus.CREATED.value(), "Organization added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<OrganizationDto>> updateOrganization(
            @PathVariable("id") Long id,
            @RequestBody OrganizationDto organizationDto) {
        log.info("PUT /organizations/{} - Updating organization", id);
        OrganizationDto updatedOrg = organizationService.updateOrganization(id, organizationDto);
        CommonResponse<OrganizationDto> response = CommonResponse.success(
                updatedOrg, HttpStatus.OK.value(), "Organization updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteOrganization(@PathVariable("id") Long id) {
        log.info("DELETE /organizations/{} - Deleting organization", id);
        organizationService.deleteOrganization(id);
        CommonResponse<Void> response = CommonResponse.success(
                null, HttpStatus.OK.value(), "Organization deleted successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getStudentCount/{id}")
    public ResponseEntity<CommonResponse<Long>> getStudentCount(@PathVariable("id") Long id) {
        log.info("GET /organizations/getStudentCount/{} - Fetching student count", id);
        Long studentCount = organizationService.getStudentCount(id);
        CommonResponse<Long> response = CommonResponse.success(
                studentCount, HttpStatus.OK.value(), "Student count fetched successfully");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}/instructor-count")
    public ResponseEntity<CommonResponse<Long>> getInstructorCount(@PathVariable("id") Long id) {
        log.info("GET /organizations/{}/instructor-count - Fetching instructor count", id);
        Long instructorCount = organizationService.getInstructorCount(id);
        CommonResponse<Long> response = CommonResponse.success(
                instructorCount, HttpStatus.OK.value(), "Instructor count fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/course-student-counts")
    public ResponseEntity<CommonResponse<List<CourseStudentCountDto>>> getStudentCountInEachCourse(@PathVariable("id") Long id) {
        log.info("GET /organizations/{}/course-student-counts - Fetching student count in each course", id);
        List<CourseStudentCountDto> courseStudentCounts = organizationService.getStudentCountInEachCourse(id);
        CommonResponse<List<CourseStudentCountDto>> response = CommonResponse.success(
                courseStudentCounts, HttpStatus.OK.value(), "Course student counts fetched successfully");
        return ResponseEntity.ok(response);
    }
}