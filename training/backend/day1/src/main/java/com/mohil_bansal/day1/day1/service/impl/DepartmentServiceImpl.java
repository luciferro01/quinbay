package com.mohil_bansal.day1.day1.service.impl;

import com.mohil_bansal.day1.day1.DTO.AddressDTO;
import com.mohil_bansal.day1.day1.DTO.DepartmentDTO;
import com.mohil_bansal.day1.day1.entity.Address;
import com.mohil_bansal.day1.day1.entity.Department;
import com.mohil_bansal.day1.day1.repo.DepartmentRepository;
import com.mohil_bansal.day1.day1.service.DepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {


    @Autowired
    DepartmentRepository departmentRepository;


    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        BeanUtils.copyProperties(departmentRepository.findById(id).get(), departmentDTO);
        return departmentDTO;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> Departments = departmentRepository.findAll();
        List<DepartmentDTO> DepartmentDTOList = new ArrayList<>();
        Departments.forEach(Department -> {
            DepartmentDTO DepartmentDTO = new DepartmentDTO();
            BeanUtils.copyProperties(Department, DepartmentDTO);
            DepartmentDTOList.add(DepartmentDTO);
        });
        return DepartmentDTOList;
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO department) {
        System.out.println(department);
        Department entityDepartment = new Department();
        BeanUtils.copyProperties(department, entityDepartment);
        departmentRepository.save(entityDepartment);
        return department;
    }

    @Override
    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO, Long id) {
        return null;
    }

    @Override
    public DepartmentDTO deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).get();
        DepartmentDTO departmentDTO = new DepartmentDTO();
        if (department != null) {
            departmentRepository.delete(department);
        }
        BeanUtils.copyProperties(department, departmentDTO);
        return departmentDTO;
    }

    @Override
    public long getStudentCountByDepartmentId(Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    String errorMessage = "Department not found with ID: " + departmentId;
                    return new RuntimeException(errorMessage);
                });

        return department.getStudents() != null ? department.getStudents().size() : 0;
    }
}
