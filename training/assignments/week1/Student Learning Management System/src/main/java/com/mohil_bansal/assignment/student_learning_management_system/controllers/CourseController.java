package com.mohil_bansal.assignment.student_learning_management_system.controllers;


import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseDetailsDto;
import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Course;
import com.mohil_bansal.assignment.student_learning_management_system.services.CourseService;
import com.mohil_bansal.assignment.student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @GetMapping
    public ResponseEntity<CommonResponse<List<CourseDto>>> getAllCourses() {
        log.info("GET /courses");
        List<Course> courses = courseService.getAllCourses();
        List<CourseDto> dtos = courses.stream().map(course -> {
            CourseDto dto = new CourseDto();
            BeanUtils.copyProperties(course, dto);
            dto.setOrganizationId(course.getOrganization().getOrganizationId());
            dto.setInstructorId(course.getInstructor() != null ? course.getInstructor().getInstructorId() : null);
            return dto;
        }).collect(Collectors.toList());
        CommonResponse<List<CourseDto>> response = CommonResponse.success(dtos, 200, "Courses fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CourseDto>> getCourseById(@PathVariable("id") Long id) {
        log.info("GET /courses/{}", id);
        Course course = courseService.getCourseById(id);
        CourseDto dto = new CourseDto();
        BeanUtils.copyProperties(course, dto);
        dto.setOrganizationId(course.getOrganization().getOrganizationId());
        dto.setInstructorId(course.getInstructor() != null ? course.getInstructor().getInstructorId() : null);
        CommonResponse<CourseDto> response = CommonResponse.success(dto, 200, "Course fetched successfully");
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}/details")
    public ResponseEntity<CommonResponse<CourseDetailsDto>> getCourseDetailsById(@PathVariable("id") Long id) {
        log.info("GET /courses/{}/details - Fetching course details by ID", id);
        CourseDetailsDto courseDetails = courseService.getCourseDetailsById(id);
        CommonResponse<CourseDetailsDto> response = CommonResponse.success(
                courseDetails, HttpStatus.OK.value(), "Course details fetched successfully");
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<CommonResponse<CourseDto>> addCourse(@RequestParam("orgId") Long orgId,
                                                               @RequestBody Course course) {
        log.info("POST /courses - Adding course");
        Course createdCourse = courseService.addCourse(course, orgId);
        CourseDto dto = new CourseDto();
        BeanUtils.copyProperties(createdCourse, dto);
        dto.setOrganizationId(createdCourse.getOrganization().getOrganizationId());
        dto.setInstructorId(createdCourse.getInstructor() != null ? createdCourse.getInstructor().getInstructorId() : null);
        CommonResponse<CourseDto> response = CommonResponse.success(dto, 200, "Course added successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteCourse(@PathVariable("id") Long id) {
        log.info("DELETE /courses/{} - Deleting course", id);
        courseService.deleteCourse(id);
        CommonResponse<Void> response = CommonResponse.success(null, 200, "Course deleted successfully");
        return ResponseEntity.ok(response);
    }
}
