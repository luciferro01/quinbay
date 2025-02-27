package com.mohil_bansal.assignment.feign.service;

import com.mohil_bansal.assignment.feign.dto.RequestStudentDto;
import com.mohil_bansal.assignment.feign.dto.ResponseStudentDto;
import com.mohil_bansal.assignment.feign.utils.CommonResponse;


public interface StudentFeignService {

    CommonResponse<ResponseStudentDto> addStudent(RequestStudentDto requestStudentDto, String orgId, Boolean isMongo);

    CommonResponse<ResponseStudentDto> getStudent(String studentId, Boolean isMongo);
}
