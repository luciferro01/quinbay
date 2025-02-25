package com.mohil_bansal_day2.day2_mongo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document(collection = "Employee")
@Getter
@Setter
public class Employee implements Serializable {
    @Id
    private String employeeId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Date dateOfJoining;
    private boolean active;
}
