package com.mohil_bansal.assignment.com_mohil_bansal_assignment_mongo_student_learning_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCaching
@EnableSwagger2
public class MongoStudentLearningManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoStudentLearningManagementSystemApplication.class, args);
    }

}
