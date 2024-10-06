package org.example.config;

import org.example.bot.*;
import org.example.repository.BarbershopRepository;
import org.example.service.I18nServiceImpl;
import org.example.service.IOServiceImpl;
import org.example.service.LanguageServiceImpl;
import org.example.service.Log4j2ConsoleAndFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public IOService ioService() {
        return new IOServiceImpl(System.in, System.out);
    }

    @Bean
    public LanguageService languageService() {
        return new LanguageServiceImpl();
    }

    @Bean
    public LoggerConsole logger(){
        return new Log4j2ConsoleAndFile();
    }

    @Bean
    public I18nService i18nService() {
        return new I18nServiceImpl(logger(), languageService());
    }

    @Bean(name = "bot")
    public BarberShopBot motivatingBot(BarbershopRepository barberShopRepository) {
        return new BarberShopBot(i18nService(), ioService(), languageService(), barberShopRepository, logger());
    }

}
