package com.mohil_bansal.assignment.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableFeignClients(basePackages = "com.mohil_bansal.assignment.feign.feign")
//@ComponentScan(basePackages = "com.mohil_bansal.assignment")
@EnableFeignClients
@EnableSwagger2
public class FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }
}
