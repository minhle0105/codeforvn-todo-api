package com.codeforvn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CodeforvnTodoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeforvnTodoApiApplication.class, args);
    }

}
