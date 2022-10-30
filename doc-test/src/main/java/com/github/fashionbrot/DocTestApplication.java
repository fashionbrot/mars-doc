package com.github.fashionbrot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DocTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocTestApplication.class,args);
    }

}
