package com.mohil_bansal_day2.day2_mongo.repository;

import com.mohil_bansal_day2.day2_mongo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String>, EmployeeRepositoryCustom {
    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    Page<Employee> findAllByActiveTrue(Pageable pageable);
}
