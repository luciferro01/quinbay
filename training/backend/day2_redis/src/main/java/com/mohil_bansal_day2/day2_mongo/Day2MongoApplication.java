package com.mohil_bansal_day2.day2_mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Day2MongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Day2MongoApplication.class, args);
	}

}
