package com.mohil_bansal_day2.day2_mongo.repository.impl;

import com.mohil_bansal_day2.day2_mongo.entity.Employee;
import com.mohil_bansal_day2.day2_mongo.repository.EmployeeRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<Employee> getEmployeesByLastName(String lastName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lastName").is(lastName));
        return  mongoTemplate.find(query, Employee.class);
    }

}
