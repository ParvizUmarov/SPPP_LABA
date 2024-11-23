package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j.properties");
        SpringApplication.run(Main.class, args);
    }
}