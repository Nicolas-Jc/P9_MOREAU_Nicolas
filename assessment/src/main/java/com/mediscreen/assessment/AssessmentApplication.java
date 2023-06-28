package com.mediscreen.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class AssessmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssessmentApplication.class, args);
    }

}
