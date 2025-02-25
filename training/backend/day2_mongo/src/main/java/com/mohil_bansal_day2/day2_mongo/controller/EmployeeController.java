package com.mohil_bansal_day2.day2_mongo.controller;

import com.mohil_bansal_day2.day2_mongo.dto.EmployeeDto;
import com.mohil_bansal_day2.day2_mongo.entity.Employee;
import com.mohil_bansal_day2.day2_mongo.services.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.findAll();

            List<EmployeeDto> employeeDtos = new ArrayList<>();
            //TODO: Check whether it will work or not just trying
//           for (Employee employee : employees){
//            BeanUtils.copyProperties(employees, employeeDtos);
//           }
            for (Employee employee : employees) {
                EmployeeDto employeeDto = new EmployeeDto();
                BeanUtils.copyProperties(employee, employeeDto);
                employeeDtos.add(employeeDto);
            }
            return ResponseEntity.ok(employeeDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getEmployeeById")
    public ResponseEntity<EmployeeDto> getEmployeeById(String employeeId) {
        try {
            Employee employee = employeeService.findOne(employeeId);
            EmployeeDto employeeDto = new EmployeeDto();
            BeanUtils.copyProperties(employee, employeeDto);
            return ResponseEntity.ok(employeeDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/insertOrUpdate")
    public ResponseEntity<EmployeeDto> insertOrUpdate(@RequestBody EmployeeDto employeeDto) {
        try {
            Employee employee = new Employee();
            BeanUtils.copyProperties(employeeDto, employee);
            employee = employeeService.insertOrUpdate(employee);
            BeanUtils.copyProperties(employee, employeeDto);
            return ResponseEntity.ok(employeeDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deleteEmployee/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@RequestParam String employeeId) {
        try {
            employeeService.delete(employeeId);
            return ResponseEntity.ok("Employee Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deleteAllEmployees")
    public ResponseEntity<String> deleteAllEmployees() {
        try {
            employeeService.deleteAll();
            return ResponseEntity.ok("All Employees Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> exists(@RequestParam String employeeId) {
        try {
            return ResponseEntity.ok(employeeService.exists(employeeId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/countEmployees")
    public ResponseEntity<Long> countEmployees() {
        try {
            return ResponseEntity.ok(employeeService.count());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getEmployeeByFirstNameAndLastName")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        try {
            List<Employee> employees = employeeService.findByFirstNameAndLastName(firstName, lastName);
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            for (Employee employee : employees) {
                EmployeeDto employeeDto = new EmployeeDto();
                BeanUtils.copyProperties(employee, employeeDto);
                employeeDtos.add(employeeDto);
            }
            return ResponseEntity.ok(employeeDtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/active")
    public Page<EmployeeDto> findByActive(@RequestParam("page") int page, @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("lastName").descending().and(Sort.by("firstName").ascending()));
        Page<Employee> employees = employeeService.findAllByActive(pageable);
        List<EmployeeDto> employeeDtos = new ArrayList<>();

        employees.forEach(employee -> {
            EmployeeDto employeeDto = new EmployeeDto();
            BeanUtils.copyProperties(employee, employeeDto);
            employeeDtos.add(employeeDto);
        });

        return new PageImpl<EmployeeDto>(employeeDtos, pageable, employees.getTotalElements());
    }


    @GetMapping("/getEmployeesByLastName")
    public List<EmployeeDto> getEmployeesByLastName(@RequestParam String lastName){
        List<Employee> employees =  employeeService.getEmployeesByLastName(lastName);
        List<EmployeeDto> employeeDTOList = new ArrayList<>();
        for (Employee employee : employees){
            EmployeeDto employeeDto = new EmployeeDto();
            BeanUtils.copyProperties(employee, employeeDto);
            employeeDTOList.add(employeeDto);
        }
        return employeeDTOList;
    }
}
