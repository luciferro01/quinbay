package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Instructor;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.entity.Organization;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.exception.DataAlreadyExistsException;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.CourseRepository;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.InstructorRepository;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.repository.OrganizationRepository;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private InstructorRepository instructorRepository;

//    @Override
//    public List<CourseDto> getAllCourses() {
//        log.info("Fetching all courses");
//        List<Course> courses = courseRepository.findAll();
//        return courses.stream().map(course -> {
//            CourseDto dto = new CourseDto();
//            BeanUtils.copyProperties(course, dto);
//            return dto;
//        }).collect(Collectors.toList());
//    }

    @Override
    public Page<CourseDto> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable).map(course -> {
            CourseDto dto = new CourseDto();
            BeanUtils.copyProperties(course, dto);
            return dto;
        });
    }

    @Override
    @Cacheable(key = "#courseId", cacheNames = "courses")
    public CourseDto getCourseById(String courseId) {
        log.info("Fetching course with ID {}", courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        CourseDto dto = new CourseDto();
        BeanUtils.copyProperties(course, dto);
        return dto;
    }

    @Override
    @Transactional
    public CourseDto addCourse(CourseDto courseDto, String organizationId) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDto, course);
        log.info("Adding course with ID {}", course.getCourseId());
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        if (course.getCourseId() != null && courseRepository.findById(course.getCourseId()).isPresent()) {
            throw new DataAlreadyExistsException("Course already exists");
        }
        course.setOrganizationId(organization.getOrganizationId());
        if(course.getInstructorId() != null){
            Instructor instructor = instructorRepository.findById(course.getInstructorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
            if(instructor.getCourseId() != course.getCourseId()){
                throw new DataAlreadyExistsException("Instructor already has a course");
            }
            instructor.setCourseId(course.getCourseId());
            instructorRepository.save(instructor);
        }
        course.setStudentIds(new HashSet<>());
        course.setCourseId(null);
        Course savedCourse = courseRepository.save(course);
        CourseDto savedCourseDto = new CourseDto();
        BeanUtils.copyProperties(savedCourse, savedCourseDto);
        return savedCourseDto;
    }

    @Override
    @Transactional
    @CacheEvict(key = "#courseId", cacheNames = "courses")
    public void deleteCourse(String courseId) {
        log.info("Deleting course with ID {}", courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        if (course.getInstructorId() != null) {
            Optional<Instructor> instructor = instructorRepository.findById(course.getInstructorId());
            if (instructor.isPresent()) {
                instructor.get().setCourseId(null);
                instructorRepository.save(instructor.get());
            }
        }
        courseRepository.delete(course);
    }
}