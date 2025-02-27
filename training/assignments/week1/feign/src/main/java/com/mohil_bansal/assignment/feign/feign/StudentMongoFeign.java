package com.mohil_bansal.assignment.feign.feign;


import com.mohil_bansal.assignment.feign.dto.RequestStudentDto;
import com.mohil_bansal.assignment.feign.dto.ResponseStudentDto;
import com.mohil_bansal.assignment.feign.utils.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

//${feign.mongo.url}"
@FeignClient(value = "student-mongo", url = "http://localhost:8081/students")
public interface StudentMongoFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/{studentId}")
    CommonResponse<ResponseStudentDto> getStudent(@RequestParam String studentId);

    @RequestMapping(method = RequestMethod.POST, value = "/{orgId}")
    CommonResponse<ResponseStudentDto> addStudent(@RequestBody RequestStudentDto requestStudentDto, @PathVariable String orgId);

}
