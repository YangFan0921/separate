package com.example.separate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
public class SeparateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeparateApplication.class, args);
    }



}
