package com.mohil_bansal.assignment.student_learning_management_system.services;

import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseStudentCountDto;
import com.mohil_bansal.assignment.student_learning_management_system.dto.OrganizationDto;

import java.util.List;

public interface OrganizationService {

    List<OrganizationDto> getAllOrganizations();

    OrganizationDto getOrganizationById(Long organizationId);

    OrganizationDto addOrganization(OrganizationDto organizationDto);

    OrganizationDto updateOrganization(Long organizationId, OrganizationDto organizationDto);

    void deleteOrganization(Long organizationId);

    Long getStudentCount(Long organizationId);

    Long getInstructorCount(Long organizationId);

    List<CourseStudentCountDto> getStudentCountInEachCourse(Long organizationId);
}