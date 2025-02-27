package com.mohil_bansal.assignment.feign.feign;

import com.mohil_bansal.assignment.feign.dto.RequestStudentDto;
import com.mohil_bansal.assignment.feign.dto.ResponseStudentDto;
import com.mohil_bansal.assignment.feign.utils.CommonResponse;
import org.springframework.http.HttpStatus;

public class StudentPostgresFeignImpl implements StudentPostgresFeign{
    @Override
    public CommonResponse<ResponseStudentDto> getStudent(String studentId) {
        return CommonResponse.failure("Internal Server Error", 500);
    }

    @Override
    public CommonResponse<ResponseStudentDto> addStudent(RequestStudentDto requestStudentDto, Long orgId) {
        return CommonResponse.failure("Internal Server Error", 500);
    }
}
