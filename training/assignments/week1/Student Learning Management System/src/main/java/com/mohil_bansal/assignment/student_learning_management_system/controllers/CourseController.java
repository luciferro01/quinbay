package com.mohil_bansal.assignment.student_learning_management_system.controllers;

import com.mohil_bansal.assignment.student_learning_management_system.dto.CourseDto;
import com.mohil_bansal.assignment.student_learning_management_system.services.CourseService;
import com.mohil_bansal.assignment.student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<CourseDto>>> getAllCourses() {
        log.info("GET /courses");
        List<CourseDto> courses = courseService.getAllCourses();
        CommonResponse<List<CourseDto>> response = CommonResponse.success(courses, 200, "Courses fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CourseDto>> getCourseById(@PathVariable("id") Long id) {
        log.info("GET /courses/{}", id);
        CourseDto course = courseService.getCourseById(id);
        CommonResponse<CourseDto> response = CommonResponse.success(course, 200, "Course fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CourseDto>> addCourse(@RequestParam("orgId") Long orgId,
                                                               @RequestBody CourseDto courseDto) {
        log.info("POST /courses - Adding course");
        CourseDto course = courseService.addCourse(courseDto, orgId);
        CommonResponse<CourseDto> response = CommonResponse.success(course, 200, "Course added successfully");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<CourseDto>> updateCourse(@PathVariable("id") Long id,
                                                                  @RequestBody CourseDto courseDto) {
        log.info("PATCH /courses/{} - Updating course", id);
        CourseDto course = courseService.updateCourse(id, courseDto);
        CommonResponse<CourseDto> response = CommonResponse.success(course, 200, "Course updated successfully");
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