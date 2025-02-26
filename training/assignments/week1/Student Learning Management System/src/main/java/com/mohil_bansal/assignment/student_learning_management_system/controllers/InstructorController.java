package com.mohil_bansal.assignment.student_learning_management_system.controllers;


import com.mohil_bansal.assignment.student_learning_management_system.dto.InstructorDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.Instructor;
import com.mohil_bansal.assignment.student_learning_management_system.services.InstructorService;
import com.mohil_bansal.assignment.student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<InstructorDto>>> getAllInstructors() {
        log.info("GET /instructors");
        List<Instructor> instructors = instructorService.getAllInstructors();
        List<InstructorDto> dtos = instructors.stream().map(instructor -> {
            InstructorDto dto = new InstructorDto();
            BeanUtils.copyProperties(instructor, dto);
            dto.setOrganizationId(instructor.getOrganization().getOrganizationId());
            dto.setCourseId(instructor.getCourse() != null ? instructor.getCourse().getCourseId() : null);
            return dto;
        }).collect(Collectors.toList());
        CommonResponse<List<InstructorDto>> response = CommonResponse.success(dtos, 200, "Instructors fetched successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<InstructorDto>> getInstructorById(@PathVariable("id") Long id) {
        log.info("GET /instructors/{}", id);
        Instructor instructor = instructorService.findInstructionById(id);
        InstructorDto dto = new InstructorDto();
        BeanUtils.copyProperties(instructor, dto);
        dto.setOrganizationId(instructor.getOrganization().getOrganizationId());
        dto.setCourseId(instructor.getCourse() != null ? instructor.getCourse().getCourseId() : null);
        CommonResponse<InstructorDto> response = CommonResponse.success(dto, 200, "Instructor fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerInstructorToCourse")
    public ResponseEntity<CommonResponse<InstructorDto>> registerInstructorToCourse(@RequestParam("instructorId") Long instructorId,
                                                                                    @RequestParam("courseId") Long courseId) {
        log.info("POST /instructors/registerInstructorToCourse - Registering instructor to course");
        Instructor instructor = instructorService.registerInstructorToCourse(instructorId, courseId);
        InstructorDto dto = new InstructorDto();
        BeanUtils.copyProperties(instructor, dto);
        dto.setOrganizationId(instructor.getOrganization().getOrganizationId());
        dto.setCourseId(instructor.getCourse() != null ? instructor.getCourse().getCourseId() : null);
        CommonResponse<InstructorDto> response = CommonResponse.success(dto, 200, "Instructor registered to course successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deRegisterInstructorFromCourse")
    public ResponseEntity<CommonResponse<InstructorDto>> deRegisterInstructorToCourse(Long instructorId){
        log.info("POST /instructors/deRegisterInstructorFromCourse - DeRegistering instructor from course");
        Instructor instructor = instructorService.deRegisterInstructorFromCourse(instructorId);
        InstructorDto dto = new InstructorDto();
        BeanUtils.copyProperties(instructor, dto);
        dto.setOrganizationId(instructor.getOrganization().getOrganizationId());
        CommonResponse<InstructorDto> response = CommonResponse.success(dto, 200, "Instructor de-registered from course successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<InstructorDto>> addInstructor(@RequestParam("orgId") Long orgId,
                                                                       @RequestBody Instructor instructor) {
        log.info("POST /instructors - Adding instructor");
        Instructor createdInstructor = instructorService.addInstructor(instructor, orgId);
        InstructorDto dto = new InstructorDto();
        BeanUtils.copyProperties(createdInstructor, dto);
        dto.setOrganizationId(createdInstructor.getOrganization().getOrganizationId());
        dto.setCourseId(createdInstructor.getCourse() != null ? createdInstructor.getCourse().getCourseId() : null);
        CommonResponse<InstructorDto> response = CommonResponse.success(dto, 200, "Instructor added successfully");
        return ResponseEntity.ok(response);
    }

    //Convert it into patch mapping
    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<InstructorDto>> updateInstructor(@PathVariable("id") Long id,
                                                                          @RequestBody Instructor instructor) {
        log.info("PUT /instructors/{} - Updating instructor", id);
        Instructor updatedInstructor = instructorService.updateInstructor(id, instructor);
        InstructorDto dto = new InstructorDto();
        BeanUtils.copyProperties(updatedInstructor, dto);
        dto.setOrganizationId(updatedInstructor.getOrganization().getOrganizationId());
        dto.setCourseId(updatedInstructor.getCourse() != null ? updatedInstructor.getCourse().getCourseId() : null);
        CommonResponse<InstructorDto> response = CommonResponse.success(dto, 200, "Instructor updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteInstructor(@PathVariable("id") Long id) {
        log.info("DELETE /instructors/{} - Deleting instructor", id);
        instructorService.deleteInstructor(id);
        CommonResponse<Void> response = CommonResponse.success(null, 200, "Instructor deleted successfully");
        return ResponseEntity.ok(response);
    }
}

