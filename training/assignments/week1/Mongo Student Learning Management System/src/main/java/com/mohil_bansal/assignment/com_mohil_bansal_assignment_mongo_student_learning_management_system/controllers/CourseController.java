package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.controllers;

import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.dto.CourseDto;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.services.CourseService;
import com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

//    @GetMapping
//    public ResponseEntity<CommonResponse<List<CourseDto>>> getAllCourses() {
//        log.info("GET /courses");
//        List<CourseDto> dtos = courseService.getAllCourses();
//        CommonResponse<List<CourseDto>> response = CommonResponse.success(dtos, 200, "Courses fetched successfully");
//        return ResponseEntity.ok(response);
//    }

    @GetMapping
    public ResponseEntity<CommonResponse<PageImpl<CourseDto>>> getAllCourses(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("GET /courses");
        Pageable pageable = PageRequest.of(page, size);
        Page<CourseDto> dtos = courseService.getAllCourses(pageable);
        CommonResponse<PageImpl<CourseDto>> response = CommonResponse.success(new PageImpl<>(dtos.getContent(), pageable, dtos.getTotalPages()), 200, "Courses fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CourseDto>> getCourseById(@PathVariable("id") String id) {
        log.info("GET /courses/{}", id);
        CourseDto dto = courseService.getCourseById(id);
        CommonResponse<CourseDto> response = CommonResponse.success(dto, 200, "Course fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<CourseDto>> addCourse(@RequestParam("orgId") String orgId,
                                                               @RequestBody CourseDto course) {
        log.info("POST /courses - Adding course");
        CourseDto createdCourse = courseService.addCourse(course, orgId);
        CommonResponse<CourseDto> response = CommonResponse.success(createdCourse, 200, "Course added successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteCourse(@PathVariable("id") String id) {
        log.info("DELETE /courses/{} - Deleting course", id);
        courseService.deleteCourse(id);
        CommonResponse<Void> response = CommonResponse.success(null, 200, "Course deleted successfully");
        return ResponseEntity.ok(response);
    }
}