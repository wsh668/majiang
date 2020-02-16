package com.example.majiang.majiang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.majiang.majiang.mapper")
@SpringBootApplication
public class MajiangApplication {

    public static void main(String[] args) {
        SpringApplication.run(MajiangApplication.class, args);
    }

}
