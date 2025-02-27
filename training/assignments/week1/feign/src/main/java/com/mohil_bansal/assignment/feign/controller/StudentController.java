package com.mohil_bansal.assignment.feign.controller;

import com.mohil_bansal.assignment.feign.dto.RequestStudentDto;
import com.mohil_bansal.assignment.feign.dto.ResponseStudentDto;
import com.mohil_bansal.assignment.feign.service.StudentFeignService;
import com.mohil_bansal.assignment.feign.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentFeignService studentFeignService;


    @GetMapping("/{studentId}/{isMongo}")
    public CommonResponse<ResponseStudentDto> getStudent(@PathVariable String studentId, @PathVariable Boolean isMongo) {
//        return CommonResponse.success(studentFeignService.getStudent(studentId, isMongo), 200, "Student fetched successfully");
        return studentFeignService.getStudent(studentId, isMongo);
    }


    @PostMapping("/{orgId}/{isMongo}")
    public CommonResponse<ResponseStudentDto> addStudent(@PathVariable String orgId, @RequestBody RequestStudentDto requestStudentDto, @RequestParam Boolean isMongo) {
//        return CommonResponse.success(studentFeignService.addStudent(requestStudentDto, orgId, isMongo),200, "Student fetched successfully");
      return  studentFeignService.addStudent(requestStudentDto, orgId, isMongo);
    }
}
