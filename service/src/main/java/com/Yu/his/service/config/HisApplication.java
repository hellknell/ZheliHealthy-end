package com.Yu.his.service.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/3 1:25
 */
@SpringBootApplication
@EnableAsync
//@ConfigurationPropertiesScan("com.Yu.his.minio.util")
@ServletComponentScan(basePackages = "com.Yu.his.service.config.xss")
@ComponentScan("com.Yu.his")
@EnableCaching
@MapperScan(basePackages = "com.Yu.his.service.mapper")
public class HisApplication {
    public static void main(String[] args) {
        SpringApplication.run(HisApplication.class, args);
    }
}
