package org.example;

import org.example.bot.BarberShopBot;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Main {
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j.properties");
        new AnnotationConfigApplicationContext(Main.class)
                .getBean("bot", BarberShopBot.class)
                .conversation();
    }
}