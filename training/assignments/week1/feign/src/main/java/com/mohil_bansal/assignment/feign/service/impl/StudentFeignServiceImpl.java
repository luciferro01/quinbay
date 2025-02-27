package com.mohil_bansal.assignment.feign.service.impl;

import com.mohil_bansal.assignment.feign.dto.RequestStudentDto;
import com.mohil_bansal.assignment.feign.dto.ResponseStudentDto;
import com.mohil_bansal.assignment.feign.feign.StudentMongoFeign;
import com.mohil_bansal.assignment.feign.feign.StudentPostgresFeign;
import com.mohil_bansal.assignment.feign.service.StudentFeignService;
import com.mohil_bansal.assignment.feign.utils.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class StudentFeignServiceImpl implements StudentFeignService {

    @Autowired
    private StudentMongoFeign studentMongoFeign;

    @Autowired
    private StudentPostgresFeign studentPostgresFeign;
    @Override
    public CommonResponse<ResponseStudentDto> addStudent(RequestStudentDto requestStudentDto, String orgId, Boolean isMongo) {
        if(isMongo){
            return studentMongoFeign.addStudent(requestStudentDto, orgId);
        }else{
            return studentPostgresFeign.addStudent(requestStudentDto, Long.parseLong(orgId));
        }
    }

    @Override
    public CommonResponse<ResponseStudentDto> getStudent(String studentId, Boolean isMongo) {
      if(isMongo){
            return studentMongoFeign.getStudent(studentId);
      }
      else {
            return studentPostgresFeign.getStudent(studentId);
      }
    }
}
