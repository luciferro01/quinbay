package com.mohil_bansal_day2.day2_mongo.repository;

import com.mohil_bansal_day2.day2_mongo.entity.Employee;
import java.util.List;
public interface EmployeeRepositoryCustom {

    List<Employee> getEmployeesByLastName(String lastName);
}
