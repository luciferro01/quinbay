package com.mohil_bansal.assignment.student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseStudentCountDto;
import com.mohil_bansal.assignment.student_learning_management_system.dto.OrganizationDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Organization;
import com.mohil_bansal.assignment.student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.student_learning_management_system.repository.OrganizationRepository;
import com.mohil_bansal.assignment.student_learning_management_system.services.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<OrganizationDto> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrganizationDto getOrganizationById(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + organizationId));
        return convertToDto(organization);
    }

    @Override
    public OrganizationDto addOrganization(OrganizationDto organizationDto) {
        Organization organization = convertToEntity(organizationDto);
        Organization savedOrganization = organizationRepository.save(organization);
        return convertToDto(savedOrganization);
    }

    @Override
    public OrganizationDto updateOrganization(Long organizationId, OrganizationDto organizationDto) {
        Organization existingOrganization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + organizationId));

        // Update only the fields that can be changed
        existingOrganization.setOrganizationName(organizationDto.getOrganizationName());
//        existingOrganization.setOrganizationId(organizationDto.getOrganizationId());
        Organization updatedOrganization = organizationRepository.save(existingOrganization);
        return convertToDto(updatedOrganization);
    }

    @Override
    public void deleteOrganization(Long organizationId) {
        if (!organizationRepository.existsById(organizationId)) {
            throw new ResourceNotFoundException("Organization not found with id: " + organizationId);
        }
        organizationRepository.deleteById(organizationId);
    }

    public Long getStudentCount(Long orgId) {
        return organizationRepository.getStudentCount(orgId);
    }


    @Override
    @Transactional(readOnly = true)
    public Long getInstructorCount(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + organizationId));
//        return organization.getInstructors() != null ? (long) organization.getInstructors().size() : 0L;
        return organizationRepository.getInstructorCount(organizationId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseStudentCountDto> getStudentCountInEachCourse(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + organizationId));

//        return organization.getCourses().stream()
//                .map(this::convertToCourseStatDto)
//                .collect(Collectors.toList());
        return organizationRepository.getStudentCountInEachCourse(organizationId);
    }


    private CourseStudentCountDto convertToCourseStatDto(Course course) {
        CourseStudentCountDto courseDto = new CourseStudentCountDto();
        courseDto.setCourseId(course.getCourseId());
        courseDto.setCourseName(course.getCourseName());
        courseDto.setStudentCount(course.getStudentCourses() != null ? course.getStudentCourses().size() : 0L);
        return courseDto;
    }

    private OrganizationDto convertToDto(Organization organization) {
        OrganizationDto dto = new OrganizationDto();
        BeanUtils.copyProperties(organization, dto);
        return dto;
    }

    private Organization convertToEntity(OrganizationDto dto) {
        Organization entity = new Organization();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }


}