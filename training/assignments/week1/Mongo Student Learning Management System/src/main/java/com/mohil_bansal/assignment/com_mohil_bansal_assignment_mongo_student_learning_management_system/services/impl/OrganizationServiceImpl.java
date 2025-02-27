package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseDetailsDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseEnrollmentStatsDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.InstructorDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.OrganizationDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.CourseStatus;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Instructor;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Organization;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Student;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.exception.DataAlreadyExistsException;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.OrganizationRepository;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<OrganizationDto> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream().map(org -> {
            OrganizationDto dto = new OrganizationDto();
            BeanUtils.copyProperties(org, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public OrganizationDto getOrganizationById(String organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        OrganizationDto dto = new OrganizationDto();
        BeanUtils.copyProperties(organization, dto);
        return dto;
    }

    @Override
    public OrganizationDto addOrganization(OrganizationDto organizationDto) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationDto, organization);
        if(organizationDto.getOrganizationId() != null) {
         if(organizationRepository.findById(organizationDto.getOrganizationId()).isPresent()){
            throw new DataAlreadyExistsException("Organization already exists");
         }
        }
        organization.setOrganizationId(null);
        Organization savedOrganization = organizationRepository.save(organization);
        OrganizationDto dto = new OrganizationDto();
        BeanUtils.copyProperties(savedOrganization, dto);
        return dto;
    }

    @Override
    public OrganizationDto updateOrganization(String organizationId, OrganizationDto updatedOrganizationDto) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        BeanUtils.copyProperties(updatedOrganizationDto, organization);
        organization.setOrganizationId(organizationId);
        Organization updatedOrganization = organizationRepository.save(organization);
        OrganizationDto dto = new OrganizationDto();
        BeanUtils.copyProperties(updatedOrganization, dto);
        return dto;
    }

    @Override
    public void deleteOrganization(String organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        organizationRepository.delete(organization);
    }

    @Override
    public long getStudentCount(String orgId) {
        log.info("Fetching student count for organization: {}", orgId);
        return organizationRepository.getStudentCount(orgId);
    }

    @Override
    public List<CourseEnrollmentStatsDto> getStudentCountByCourse(String orgId) {
        log.info("Fetching student count by course for organization: {}", orgId);
        return organizationRepository.getStudentCountByCourse(orgId);
    }

    @Override
    public List<InstructorDto> getInstructorsByCourse(String courseId) {
        log.info("Fetching instructors for course: {}", courseId);
        List<Instructor> instructors = organizationRepository.getInstructorsByCourse(courseId);
        return instructors.stream().map(instructor -> {
            InstructorDto dto = new InstructorDto();
            BeanUtils.copyProperties(instructor, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public long getInstructorCount(String orgId) {
        log.info("Fetching instructor count for organization: {}", orgId);
        return organizationRepository.getInstructorCount(orgId);
    }

    @Override
    public CourseDetailsDto getCourseDetailsById(String courseId) {
        log.info("Fetching course details for course: {}", courseId);
        Map<String, Object> details = organizationRepository.getCourseDetailsById(courseId);
        CourseDetailsDto dto = new CourseDetailsDto();
        BeanUtils.copyProperties(details.get("course"), dto);

        InstructorDto instructorDto = new InstructorDto();
        BeanUtils.copyProperties(details.get("instructor"), instructorDto);
        dto.setInstructor(instructorDto);

        List<StudentDto> studentDtos = ((List<Student>) details.get("students")).stream()
                .map(student -> {
                    StudentDto studentDto = new StudentDto();
                    BeanUtils.copyProperties(student, studentDto);
                    return studentDto;
                })
                .collect(Collectors.toList());
        dto.setEnrolledStudents(studentDtos);

        return dto;
    }

    @Override
    public List<StudentDto> getStudentsByCourseStatus(CourseStatus status) {
        log.info("Fetching students by course status: {}", status);
        List<Student> students = organizationRepository.getStudentsByCourseStatus(status);
        return students.stream().map(student -> {
            StudentDto dto = new StudentDto();
            BeanUtils.copyProperties(student, dto);
            return dto;
        }).collect(Collectors.toList());
    }
}
