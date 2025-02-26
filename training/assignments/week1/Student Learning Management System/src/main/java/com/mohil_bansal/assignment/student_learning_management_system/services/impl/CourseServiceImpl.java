package com.mohil_bansal.assignment.student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseDetailsDto;
import com.mohil_bansal.assignment.student_learning_management_system.dto.StudentDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Organization;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Student;
import com.mohil_bansal.assignment.student_learning_management_system.exception.DataAlreadyExistsException;
import com.mohil_bansal.assignment.student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.student_learning_management_system.repository.CourseRepository;
import com.mohil_bansal.assignment.student_learning_management_system.repository.OrganizationRepository;
import com.mohil_bansal.assignment.student_learning_management_system.services.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final OrganizationRepository organizationRepository;

    public CourseServiceImpl(CourseRepository courseRepository, OrganizationRepository organizationRepository) {
        this.courseRepository = courseRepository;
        this.organizationRepository = organizationRepository;
    }

    public List<Course> getAllCourses() {
        log.info("Fetching all courses");
        return courseRepository.findAll();
    }

    @Cacheable(key = "#courseId", cacheNames = "courses")
    public Course getCourseById(Long courseId) {
        log.info("Fetching course with ID {}", courseId);
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
    }

    @Transactional
//    @CachePut(key = "#course.courseId", cacheNames = "courses")
    public Course addCourse(Course course, Long organizationId) {
        log.info("Adding course with ID {}", course.getCourseId());
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));
        if(course.getCourseId() != null && courseRepository.findById(course.getCourseId()).isPresent()){
            throw new DataAlreadyExistsException("Course already exists");
        }
        course.setOrganization(organization);
        return courseRepository.save(course);
    }

    @Transactional
    @CacheEvict(key = "#courseId", cacheNames = "courses")
    public void deleteCourse(Long courseId) {
        log.info("Deleting course with ID {}", courseId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        courseRepository.delete(course);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDetailsDto getCourseDetailsById(Long courseId) {
//        Course course = courseRepository.findCourseWithDetailsById(courseId);
//        if (course == null) {
//            throw new ResourceNotFoundException("Course not found with id: " + courseId);
//        }
//
//        CourseDetailsDto courseDetailsDto = new CourseDetailsDto();
//        courseDetailsDto.setCourseId(course.getCourseId());
//        courseDetailsDto.setCourseName(course.getCourseName());
//        courseDetailsDto.setCourseFee(course.getCourseFee());
//        courseDetailsDto.setCourseStatus(course.getCourseStatus());
//
//        if (course.getOrganization() != null) {
//            courseDetailsDto.setOrganizationId(course.getOrganization().getOrganizationId());
//            courseDetailsDto.setOrganizationName(course.getOrganization().getOrganizationName());
//        }
//
//        if (course.getInstructor() != null) {
//            courseDetailsDto.setInstructorId(course.getInstructor().getInstructorId());
//            courseDetailsDto.setInstructorName(course.getInstructor().getInstructorName());
//        }
//
//        List<StudentDto> studentDtos = new ArrayList<>();
//        if (course.getStudentCourses() != null) {
//            studentDtos = course.getStudentCourses().stream()
//                    .map(StudentCourse::getStudent)
//                    .filter(student -> student != null)
//                    .map(this::convertToStudentDto)
//                    .collect(Collectors.toList());
//        }
//        courseDetailsDto.setStudents(studentDtos);

        return courseRepository.findCourseWithDetailsById(courseId);
    }

    private StudentDto convertToStudentDto(Student student) {
        StudentDto dto = new StudentDto();
        BeanUtils.copyProperties(student, dto);
        return dto;
    }
}

