package com.mohil_bansal.assignment.student_learning_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class StudentLearningManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentLearningManagementSystemApplication.class, args);
	}
}
