package com.mohil_bansal.assignment.student_learning_management_system.services.impl;

import com.mohil_bansal.assignment.student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Instructor;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Organization;
import com.mohil_bansal.assignment.student_learning_management_system.exception.DataAlreadyExistsException;
import com.mohil_bansal.assignment.student_learning_management_system.exception.ResourceNotFoundException;
import com.mohil_bansal.assignment.student_learning_management_system.repository.CourseRepository;
import com.mohil_bansal.assignment.student_learning_management_system.repository.InstructorRepository;
import com.mohil_bansal.assignment.student_learning_management_system.repository.OrganizationRepository;
import com.mohil_bansal.assignment.student_learning_management_system.services.InstructorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CourseRepository courseRepository;


    public List<Instructor> getAllInstructors() {
        log.info("Fetching all instructors");
        return instructorRepository.findAll();
    }


    @Cacheable(key = "#instructorId", cacheNames = "instructors")
    public Instructor findInstructionById(Long instructorId) {
        log.info("Fetching instructor with ID {}", instructorId);
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
    }

    @Transactional
    public Instructor addInstructor(Instructor instructor, Long organizationId) {
        log.info("Adding instructor with ID {}", instructor.getInstructorId());
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found"));

        if(instructor.getInstructorId() != null && instructorRepository.findById(instructor.getInstructorId()).isPresent()){
            throw new DataAlreadyExistsException("Instructor already exists");
        }
        instructor.setOrganization(organization);
        return instructorRepository.save(instructor);
    }


    @Transactional
    @CachePut(key="#instructorId", cacheNames = "instructors")
    public Instructor registerInstructorToCourse(Long instructorId, Long courseId) {
        log.info("Adding instructor with ID {} to Course {}", instructorId, courseId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        if(course.getInstructor() != null){
            throw new DataAlreadyExistsException("Course already has an instructor with id - " + course.getInstructor().getInstructorId());
        }

        instructor.setCourse(course);
        return instructorRepository.save(instructor);
    }

    @Transactional


    @CachePut(key = "#instructorId", cacheNames = "instructors")
    public Instructor deRegisterInstructorFromCourse(Long instructorId) {
        log.info("Removing instructor with ID {} from Course", instructorId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));

        instructor.setCourse(null);
        return instructorRepository.save(instructor);
    }

    @Transactional
    @CachePut(key = "#instructorId", cacheNames = "instructors")
    public Instructor updateInstructor(Long instructorId, Instructor updatedInstructor) {
        log.info("Updating instructor with ID {}", instructorId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        instructor.setInstructorName(updatedInstructor.getInstructorName());
        instructor.setInstructorDob(updatedInstructor.getInstructorDob());
        return instructorRepository.save(instructor);
    }

    @Transactional
    @CacheEvict(key = "#instructionId", cacheNames = "instructors")
    public void deleteInstructor(Long instructorId) {
        log.info("Deleting instructor with ID {}", instructorId);
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("Instructor not found"));
        instructorRepository.delete(instructor);
    }
}

