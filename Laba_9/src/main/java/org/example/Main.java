package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Main {
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j.properties");
        SpringApplication.run(Main.class, args);
    }
}