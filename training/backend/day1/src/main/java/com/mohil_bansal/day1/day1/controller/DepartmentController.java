package com.mohil_bansal.day1.day1.controller;

import com.mohil_bansal.day1.day1.DTO.AddressDTO;
import com.mohil_bansal.day1.day1.DTO.DepartmentDTO;
import com.mohil_bansal.day1.day1.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {


    @Autowired
    private DepartmentServiceImpl service;


    @GetMapping("/getAllDepartments")
    public List<DepartmentDTO> getAllDepartments(){
        return service.getAllDepartments();
    }

    @PostMapping(value = "/createDepartment",consumes = "application/json")
    public DepartmentDTO createStudent(@RequestBody DepartmentDTO student) {
        System.out.println("here");
        return service.createDepartment(student);
    }

    @GetMapping("/getDepartmentById")
    public DepartmentDTO getDepartmentById(@RequestParam String id){

        return service.getDepartmentById(Long.parseLong(id));
    }
    @DeleteMapping("/deleteDepartment")
    public DepartmentDTO deleteDepartment(@RequestParam Long id){
        return service.deleteDepartment(id);
    }

    @GetMapping("/{departmentId}/student-count")
    public ResponseEntity<Long> getStudentCount(@PathVariable Long departmentId) {
        try {
            long count = service.getStudentCountByDepartmentId(departmentId);
            return ResponseEntity.ok(count);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


//    @PatchMapping("/updateDepartment")
//    public DepartmentDTO updateDepartment(@RequestBody DepartmentDTO DepartmentDTO, @RequestParam String id){
//        return service.updateDepartment(Long.parseLong(id), DepartmentDTO);
//    }

}
