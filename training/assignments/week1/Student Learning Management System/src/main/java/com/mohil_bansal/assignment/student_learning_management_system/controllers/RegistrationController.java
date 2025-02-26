package com.mohil_bansal.assignment.student_learning_management_system.controllers;


import com.mohil_bansal.assignment.student_learning_management_system.dto.RegistrationDto;
import com.mohil_bansal.assignment.student_learning_management_system.entity.StudentCourse;
import com.mohil_bansal.assignment.student_learning_management_system.services.RegistrationService;
import com.mohil_bansal.assignment.student_learning_management_system.utils.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private  RegistrationService registrationService;


    @PostMapping
    public ResponseEntity<CommonResponse<RegistrationDto>> registerStudentToCourse(@RequestBody RegistrationDto registrationDto) {
        log.info("POST /registrations - Registering student {} to course {}",
                registrationDto.getStudentId(), registrationDto.getCourseId());
        StudentCourse registration = registrationService.registerStudentToCourse(
                registrationDto.getStudentId(),
                registrationDto.getCourseId(),
                registrationDto.getCourseStatus());
        RegistrationDto dto = new RegistrationDto();
        BeanUtils.copyProperties(registration, dto);
        // Manually set IDs if needed because BeanUtils may not automatically map nested properties.
        dto.setStudentId(registration.getStudent().getStudentId());
        dto.setCourseId(registration.getCourse().getCourseId());
        CommonResponse<RegistrationDto> response = CommonResponse.success(dto, 200, "Registration successful");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> withdrawStudentFromCourse(@RequestParam Long studentId,
                                                                          @RequestParam Long courseId) {
        log.info("DELETE /registrations - Withdrawing student {} from course {}", studentId, courseId);
        registrationService.withdrawStudentFromCourse(studentId, courseId);
        CommonResponse<Void> response = CommonResponse.success(null, 200, "Student withdrawn successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<RegistrationDto>> updateCourseStatus(@RequestBody RegistrationDto registrationDto) {
        log.info("PUT /registrations - Updating course status for student {} in course {}",
                registrationDto.getStudentId(), registrationDto.getCourseId());
        StudentCourse updatedRegistration = registrationService.updateCourseStatus(
                registrationDto.getStudentId(),
                registrationDto.getCourseId(),
                registrationDto.getCourseStatus());
        RegistrationDto dto = new RegistrationDto();
        BeanUtils.copyProperties(updatedRegistration, dto);
        dto.setStudentId(updatedRegistration.getStudent().getStudentId());
        dto.setCourseId(updatedRegistration.getCourse().getCourseId());
        CommonResponse<RegistrationDto> response = CommonResponse.success(dto, 200, "Registration updated successfully");
        return ResponseEntity.ok(response);
    }
}
