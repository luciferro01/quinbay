package com.mohil_bansal_day2.day2_mongo.services.impl;

import com.mohil_bansal_day2.day2_mongo.entity.Employee;
import com.mohil_bansal_day2.day2_mongo.repository.EmployeeRepository;
import com.mohil_bansal_day2.day2_mongo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    @Cacheable(key = "#employeeId", cacheNames = "employee")
    public Employee findOne(String employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    @Override
//    @CacheEvict(value = "employee", key = "#employee.employeeId")
    @CachePut(value = "employee", key = "#employee.employeeId")
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
//        return employeeRepository.exists(employeeId);
        return employeeRepository.findById(employeeId).isPresent();
    }

    @Override
    public Long count() {
        return employeeRepository.count();
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Page<Employee> findAllByActive(Pageable pageable) {
       return employeeRepository.findAllByActiveTrue(pageable);
//        return null;
    }

    @Override
    public List<Employee> getEmployeesByLastName(String lastName) {
        return employeeRepository.getEmployeesByLastName(lastName);
    }

}
