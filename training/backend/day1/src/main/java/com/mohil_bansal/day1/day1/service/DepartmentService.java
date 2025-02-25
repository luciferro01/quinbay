package com.mohil_bansal.day1.day1.service;

import com.mohil_bansal.day1.day1.DTO.DepartmentDTO;
import com.mohil_bansal.day1.day1.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {
    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();

    DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Long id);

    DepartmentDTO deleteDepartment(Long id);

    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    long getStudentCountByDepartmentId(Long departmentId);

}
