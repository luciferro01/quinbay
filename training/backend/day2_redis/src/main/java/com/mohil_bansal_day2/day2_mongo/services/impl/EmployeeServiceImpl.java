package com.mohil_bansal_day2.day2_mongo.services.impl;

import com.mohil_bansal_day2.day2_mongo.entity.Employee;
import com.mohil_bansal_day2.day2_mongo.repository.EmployeeRepository;
import com.mohil_bansal_day2.day2_mongo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findOne(String employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    @Override
    public Employee insertOrUpdate(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void delete(String employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public void deleteAll() {
    employeeRepository.deleteAll();
    }

    @Override
    public boolean exists(String employeeId) {
        return employeeRepository.findById(employeeId).isPresent();
    }

    @Override
    public Long count() {
        return employeeRepository.count();
    }



}
