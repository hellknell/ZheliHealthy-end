package com.Yu.his.service.config;

import com.Yu.his.service.async.initAsync;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/3 1:25
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
@ServletComponentScan(basePackages = "com.Yu.his.service.config.xss")
@ComponentScan("com.Yu.his")
@EnableCaching
@Slf4j
@MapperScan(basePackages = "com.Yu.his.service.mapper")
    public class HisApplication {
        @Resource
    private initAsync initAsync;

    public static void main(String[] args) {
        SpringApplication.run(HisApplication.class, args);
    }

    @PostConstruct
    public void init() {
        log.info("异步线程开始....");
        initAsync.init();
    }
}
