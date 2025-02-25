package com.mohil_bansal_day2.day2_mongo.services;

import com.mohil_bansal_day2.day2_mongo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface EmployeeService {
    List<Employee> findAll();

    Employee findOne(String employeeId);

    Employee insertOrUpdate(Employee employee);

    void delete(String employeeId);

    void deleteAll();

    boolean exists(String employeeId);

    Long count();

    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    Page<Employee> findAllByActive(Pageable pageable);

    List<Employee> getEmployeesByLastName(String lastName);

}
