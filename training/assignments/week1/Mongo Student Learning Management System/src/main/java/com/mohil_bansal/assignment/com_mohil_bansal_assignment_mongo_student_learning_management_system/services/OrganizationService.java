package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseDetailsDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseEnrollmentStatsDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.InstructorDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.OrganizationDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationService {
//    List<OrganizationDto> getAllOrganizations();
Page<OrganizationDto> getAllOrganizations(Pageable pageable);

    OrganizationDto getOrganizationById(String organizationId);

    OrganizationDto addOrganization(OrganizationDto organizationDto);

    OrganizationDto updateOrganization(String organizationId, OrganizationDto updatedOrganizationDto);

    void deleteOrganization(String organizationId);

    long getStudentCount(String orgId);

    List<CourseEnrollmentStatsDto> getStudentCountByCourse(String orgId);

    List<InstructorDto> getInstructorsByCourse(String courseId);

    long getInstructorCount(String orgId);

    CourseDetailsDto getCourseDetailsById(String courseId);

    List<StudentDto> getStudentsByCourseStatus(CourseStatus status);
}