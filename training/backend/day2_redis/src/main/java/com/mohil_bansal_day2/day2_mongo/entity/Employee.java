package com.mohil_bansal_day2.day2_mongo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@RedisHash(value = "Employee")
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
