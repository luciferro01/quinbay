package com.mohil_bansal.assignment.student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Organization;
import com.mohil_bansal.assignment.student_learning_management_system.exception.DataAlreadyExistsException;
import com.mohil_bansal.assignment.student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.student_learning_management_system.repository.CourseRepository;
import com.mohil_bansal.assignment.student_learning_management_system.repository.OrganizationRepository;
import com.mohil_bansal.assignment.student_learning_management_system.services.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<CourseDto> getAllCourses() {
        log.info("Fetching all courses");
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(key = "#id", cacheNames = "courses")
    public CourseDto getCourseById(Long id) {
        log.info("Fetching course with ID {}", id);
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        return convertToDto(course);
    }

    @Override
    @Transactional
    public CourseDto addCourse(CourseDto courseDto, Long orgId) {
        log.info("Adding new course to organization with ID {}", orgId);
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + orgId));

        Course course = convertToEntity(courseDto);

        if(course.getCourseId() != null && courseRepository.findById(course.getCourseId()).isPresent()) {
            throw new DataAlreadyExistsException("Course already exists");
        }

        course.setOrganization(organization);
        Course savedCourse = courseRepository.save(course);
        return convertToDto(savedCourse);
    }

    @Override
    @Transactional
    @CachePut(key = "#id", cacheNames = "courses")
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        log.info("Updating course with ID {}", id);
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        existingCourse.setCourseName(courseDto.getCourseName());

        Course updatedCourse = courseRepository.save(existingCourse);
        return convertToDto(updatedCourse);
    }

    @Override
    @Transactional
    @CacheEvict(key = "#id", cacheNames = "courses")
    public void deleteCourse(Long id) {
        log.info("Deleting course with ID {}", id);
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    private CourseDto convertToDto(Course course) {
        CourseDto dto = new CourseDto();
        BeanUtils.copyProperties(course, dto);
        dto.setOrganizationId(course.getOrganization().getOrganizationId());

        if (course.getInstructor() != null) {
            dto.setInstructorId(course.getInstructor().getInstructorId());
        }

        return dto;
    }

    private Course convertToEntity(CourseDto dto) {
        Course entity = new Course();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}