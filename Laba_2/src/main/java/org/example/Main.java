package org.example;

import lombok.RequiredArgsConstructor;
import org.example.aop.AnalyzeAspect;
import org.example.bot.BarberShopBot;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import java.util.Collections;
import java.util.Map;

@Configuration
@ComponentScan(basePackages = "org.example")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@RequiredArgsConstructor
public class Main {
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile", "src/main/resources/log4j.properties");

        var context = new AnnotationConfigApplicationContext(Main.class);

        context.getBean("bot", BarberShopBot.class)
                .conversation();

        var memo = context.getBean(AnalyzeAspect.class).getMemo();
        printAllTime(memo);
    }

    static void printAllTime(Map<String, Long> memo) {
        memo.entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach((m) -> {
                    System.out.println("Method: " + m.getKey());
                    System.out.println("\ttime: " + m.getValue() + " mc ");

                });
    }

}