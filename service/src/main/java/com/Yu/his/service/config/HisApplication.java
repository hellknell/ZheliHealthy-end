package com.Yu.his.service.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/3 1:25
 */
@SpringBootApplication
@ComponentScan("com.Yu.his")
@MapperScan(basePackages = "com.Yu.his.service.mapper")
public class HisApplication {
    public static void main(String[] args) {
        SpringApplication.run(HisApplication.class, args);
    }
}
