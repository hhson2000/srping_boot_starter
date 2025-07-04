package com.example.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PracticeApplication {

    @RequestMapping("/helloword")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }
}
