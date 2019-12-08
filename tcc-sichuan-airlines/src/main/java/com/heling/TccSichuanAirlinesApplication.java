package com.heling;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.heling.mapper")
@EnableTransactionManagement
public class TccSichuanAirlinesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TccSichuanAirlinesApplication.class, args);
    }

}