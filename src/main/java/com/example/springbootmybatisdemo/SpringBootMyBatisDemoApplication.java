package com.example.springbootmybatisdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication    //告诉 Spring Boot，以这个类为入口，启动整个应用，并启用 Spring Boot 的核心功能
public class SpringBootMyBatisDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootMyBatisDemoApplication.class, args);   //启动整个 Spring Boot 应用
    }
}
